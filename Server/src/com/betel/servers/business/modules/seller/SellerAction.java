package com.betel.servers.business.modules.seller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.betel.asd.BaseAction;
import com.betel.common.Monitor;
import com.betel.consts.Action;
import com.betel.consts.Bean;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.database.RedisKeys;
import com.betel.servers.business.modules.beans.Brand;
import com.betel.servers.business.modules.beans.Profile;
import com.betel.servers.business.modules.beans.Seller;
import com.betel.servers.business.modules.profile.ProfileAction;
import com.betel.servers.business.modules.profile.ProfileVo;
import com.betel.session.Session;
import com.betel.utils.IdGenerator;
import com.betel.utils.JsonUtils;
import com.betel.utils.JwtHelper;
import com.betel.utils.TimeUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @ClassName: BuyerAction
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 23:00
 */
public class SellerAction extends BaseAction<Seller>
{
    class ScanCodeState
    {
        public final static String WaitScan = "WaitScan";
        public final static String Scanned  = "Scanned";
        public final static String Used     = "Used";
        public final static String Overdue  = "Overdue";
    }
    class ScanCodeResult
    {
        public final static String Success  = "Success";
        public final static String Fail     = "Fail";
        public final static String Accept   = "Accept";
        public final static String Refuse   = "Refuse";
    }

    final static Logger logger = Logger.getLogger(SellerAction.class);
    /**
     * 扫码最大等待时间
     */
    final static long MAX_SCAN_WAIT_TIME = 600 * 1000;

    private HashMap<String, Seller> sellerMap;

    /**
     * 记录二维码是否被扫描过
     */
    private HashMap<String, ScanInfo> qrCodeHasScanned;

    public SellerAction(Monitor monitor)
    {
        this.monitor = monitor;
        this.service = new SellerService();
        this.service.setBaseDao(new SellerDao(monitor.getDB()));

        sellerMap = new HashMap<>();
        qrCodeHasScanned = new HashMap<>();
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String method)
    {
        Session session = new Session(ctx, jsonObject);
        switch (method)
        {
            case Action.NONE:
                break;
            case Action.WEB_ADMIN_PROBE:
                webAdminProbe(session);
                break;
            case Action.WEB_RQST_SCAN_CODE_LOGIN:
                webRqstLoginQRCode(session);
                break;
            case Action.WEB_SCAN_LOGIN:// 扫码登录
                webScanCodeLogin(session);
                break;
            case Action.WEB_LOGIN:// 直接登录
                webLogin(session);
                break;
            case Action.MP_SCAN_WEB_LOGIN:
                mpScanWebLogin(session);
                break;
            default:
                break;
        }
    }
    private void webAdminProbe(Session session)
    {
        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.PROBE_STATE, true);
        sendJson.put(FieldName.PROBE_MSG, "探测返回.服务器运行正常");
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }

    // Web请求登录用的二维码
    private void webRqstLoginQRCode(Session session)
    {
        //扫码用临时id
        String scanId = "zhengnan";
        //String scanId = Long.toString(IdGenerator.getInstance().nextId());
        qrCodeHasScanned.put(scanId,new ScanInfo(SellerMnt.ScanCodeState.WaitScan,System.currentTimeMillis()));
        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.SCAN_ID, scanId);
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }
    // Web用户请求扫码登录
    private void webLogin(Session session)
    {
        String profileId = session.getRecvJson().getString("profileId");
        ProfileAction profileAction = (ProfileAction) monitor.getAction(Bean.PROFILE);
        Profile profile = profileAction.getService().getEntryById(profileId);
        String token = JwtHelper.createJWT(profileId);
        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.TOKEN,token);//登录令牌
        sendJson.put(FieldName.SELLER_INFO,JSONObject.toJSON(profile));
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }
    // Web用户请求扫码登录
    private void webScanCodeLogin(Session session)
    {
        // 获取扫码ID
        String scanId = session.getRecvJson().getString(FieldName.SCAN_ID);
        ScanInfo scanInfo = qrCodeHasScanned.get(scanId);
        JSONObject sendJson = new JSONObject();
        if(scanInfo != null)
        {
            logger.info("登录中...." + scanInfo.getState());
            switch (scanInfo.getState())
            {
                case SellerMnt.ScanCodeState.Scanned://验证通过,登录成功
                    scanInfo.setState(SellerMnt.ScanCodeState.Used);

                    //成功后返回扫码者的微信用户信息过去
                    String profileId = scanInfo.getScanProfileId();
                    ProfileAction profileAction = (ProfileAction) monitor.getAction(Bean.PROFILE);
                    Profile profile = profileAction.getService().getEntryById(profileId);
                    if (profile == null)
                    {//扫码成功但是用户数据为空
                        sendJson.put(FieldName.SCAN_STATE, SellerMnt.ScanCodeResult.Fail);
                    }else{
                        sendJson.put(FieldName.SCAN_STATE, SellerMnt.ScanCodeResult.Success);
                        String token = JwtHelper.createJWT(profileId);
                        sendJson.put(FieldName.TOKEN,token);//登录令牌
                        sendJson.put(FieldName.SELLER_INFO,JSONObject.toJSON(profile));
                    }
                    break;
                case SellerMnt.ScanCodeState.WaitScan://还在等待验证
                    if(System.currentTimeMillis() - scanInfo.getStartTime() > MAX_SCAN_WAIT_TIME)
                    {
                        scanInfo.setState(SellerMnt.ScanCodeState.Overdue);
                        sendJson.put(FieldName.SCAN_STATE, SellerMnt.ScanCodeState.Overdue);
                    }
                    else
                        sendJson.put(FieldName.SCAN_STATE, SellerMnt.ScanCodeState.WaitScan);//继续等待
                    break;
                case SellerMnt.ScanCodeState.Overdue://过期
                    sendJson.put(FieldName.SCAN_STATE, SellerMnt.ScanCodeState.Overdue);
                    break;
                default:
                    sendJson.put(FieldName.SCAN_STATE, SellerMnt.ScanCodeResult.Fail);
                    break;
            }
        }else{
            sendJson.put(FieldName.SCAN_STATE, SellerMnt.ScanCodeResult.Fail);
        }
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }
    // 小程序端扫码Web端登录
    private void mpScanWebLogin(Session session)
    {
        // 获取扫码ID
        String scanId = session.getRecvJson().getString(FieldName.SCAN_ID);
        String scanResult = session.getRecvJson().getString(FieldName.SCAN_STATE);
        ScanInfo scanInfo = qrCodeHasScanned.get(scanId);
        JSONObject sendJson = new JSONObject();
        if(scanInfo != null)
        {
            if(SellerMnt.ScanCodeResult.Accept.equals(scanResult))
            {//扫码同意
                if(scanInfo.getState() == SellerMnt.ScanCodeState.WaitScan)
                {// 扫码登录成功
                    scanInfo.setState(SellerMnt.ScanCodeState.Scanned);
                    scanInfo.setScanProfileId(session.getRecvJson().getString(RedisKeys.profile_id));
                    sendJson.put(FieldName.SCAN_STATE,scanInfo.getState());
                }else{//二维码已经过期
                    sendJson.put(FieldName.SCAN_STATE,SellerMnt.ScanCodeState.Overdue);
                }
            }else{//扫码拒绝
                sendJson.put(FieldName.SCAN_STATE,SellerMnt.ScanCodeResult.Refuse);
            }
        }else{
            sendJson.put(FieldName.SCAN_STATE,SellerMnt.ScanCodeState.Overdue);
        }
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }
}
