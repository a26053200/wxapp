package com.betel.servers.business.modules.seller;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.session.Session;
import com.betel.utils.IdGenerator;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * @ClassName: SellerMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/8 22:53
 */
public class SellerMnt extends SubMonitor
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
    class ScanInfo
    {
        public String state;
        public long startTime;

        public ScanInfo(String state, long startTime)
        {
            this.state = state;
            this.startTime = startTime;
        }
    }


    final static Logger logger = Logger.getLogger(SellerMnt.class);
    /**
     * 扫码最大等待时间
     */
    final static long MAX_SCAN_WAIT_TIME = 600 * 1000;

    public SellerMnt(Monitor base)
    {
        super(base);
    }

    private HashMap<String, SellerVo> sellerMap;

    /**
     * 记录二维码是否被扫描过
     */
    private HashMap<String, ScanInfo> qrCodeHasScanned;

    @Override
    public void Init()
    {
        super.Init();
        qrCodeHasScanned = new HashMap<>();
    }
    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction)
    {
        Session session = new Session(ctx, jsonObject);
        switch (subAction)
        {
            case Action.NONE:
                break;
            case Action.WEB_ADMIN_PROBE:
                webAdminProbe(session);
                break;
            case Action.WEB_RQST_SCAN_CODE_LOGIN:
                webRqstLoginQRCode(session);
                break;
            case Action.WEB_LOGIN:
                webScanCodeLogin(session);
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
        //标识客户端ID
        String webClientId = Long.toString(IdGenerator.getInstance().nextId());
        sendJson.put(FieldName.PROBE_STATE, true);
        sendJson.put(FieldName.PROBE_MSG, "探测返回.服务器运行正常");
        sendJson.put(FieldName.WEB_CLIENT_ID, webClientId);
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }

    // Web请求登录用的二维码
    private void webRqstLoginQRCode(Session session)
    {
        //标识客户端ID
        //String webClientId = session.getRecvJson().getString(FieldName.WEB_CLIENT_ID);
        //扫码用临时id
        //String scanId = Long.toString(IdGenerator.getInstance().nextId());
        String scanId = "zhengnan";
        qrCodeHasScanned.put(scanId,new ScanInfo(ScanCodeState.WaitScan,System.currentTimeMillis()));
        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.SCAN_ID, scanId);
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
            logger.info("登录中...." + scanInfo.state);
            switch (scanInfo.state)
            {
                case ScanCodeState.Scanned://验证通过,登录成功
                    scanInfo.state = ScanCodeState.Used;
                    sendJson.put(FieldName.SCAN_STATE, ScanCodeResult.Success);
                    break;
                case ScanCodeState.WaitScan://还在等待验证
                    if(System.currentTimeMillis() - scanInfo.startTime > MAX_SCAN_WAIT_TIME)
                    {
                        scanInfo.state = ScanCodeState.Overdue  ;
                        sendJson.put(FieldName.SCAN_STATE, ScanCodeState.Overdue);
                    }
                    else
                        sendJson.put(FieldName.SCAN_STATE, ScanCodeState.WaitScan);//继续等待
                    break;
                case ScanCodeState.Overdue://过期
                    sendJson.put(FieldName.SCAN_STATE, ScanCodeState.Overdue);
                    break;
                default:
                    sendJson.put(FieldName.SCAN_STATE, ScanCodeResult.Fail);
                    break;
            }
        }else{
            sendJson.put(FieldName.SCAN_STATE, ScanCodeResult.Fail);
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
            if(ScanCodeResult.Accept.equals(scanResult))
            {
                if(scanInfo.state == ScanCodeState.WaitScan)
                {// 只有新的验证码才能
                    scanInfo.state = ScanCodeState.Scanned;
                }else{

                }
            }
            sendJson.put(FieldName.SCAN_STATE,scanInfo.state);
        }else{
            sendJson.put(FieldName.SCAN_STATE,ScanCodeState.Overdue);
        }
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }
}
