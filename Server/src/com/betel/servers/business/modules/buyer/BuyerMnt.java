package com.betel.servers.business.modules.buyer;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.servers.business.modules.profile.ProfileVo;
import com.betel.session.Session;
import com.betel.utils.HttpRequest;
import com.betel.utils.IdGenerator;
import com.betel.utils.StringUtils;
import com.betel.utils.TimeUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;

/**
 * @ClassName: BuyerMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/13 0:21
 */
public class BuyerMnt extends SubMonitor
{
    final static Logger logger = Logger.getLogger(BuyerMnt.class);

    private final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    private final String WX_LOGIN_PARAM = "appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    private final String APP_ID = "wx4bf12d9a5d200dfb";

    private final String APP_SECRET = "94d170fe988568d97d3cb5e05ee42cfd";

    private HashMap<String, BuyerVo> buyerMap;

    public BuyerMnt(Monitor base)
    {
        super(base);
        buyerMap = new HashMap<>();
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction)
    {
        Session session = new Session(ctx, jsonObject);
        switch (subAction)
        {
            case Action.NONE:

                break;
            case Action.MP_PROBE:
                mp_probe(session);
                break;
            case Action.MP_LOGIN_WX_SERVER:
                login_wx_server(session);
                break;
            default:
                break;
        }
    }

    private void mp_probe(Session session)
    {
        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.PROBE_STATE, true);
        sendJson.put(FieldName.PROBE_MSG, "探测返回.服务器运行正常");
        rspdClient(session, sendJson, ServerName.CLIENT_MP);
    }

    // 微信用户登录
    private void login_wx_server(Session session)
    {
        String nowTime = TimeUtils.date2String(new Date());
        String channelId = session.getChannelId();
        String code = session.getRecvJson().getString("code");

        //请求微信登录接口服务器返回OpenId 和 UnionId
        String wxApiRspd = HttpRequest.sendPost(WX_LOGIN_URL, String.format(WX_LOGIN_PARAM, APP_ID, APP_SECRET, code));
        //"openid":"oqZlN5Qw3-Ch1WqidzgW9DX5uGg0","session_key":"8bKQ0+2Seux3Ce6yEs5BNw=="
        logger.info(wxApiRspd);

        // 买家登录信息
        JSONObject loginInfoJson = JSONObject.parseObject(wxApiRspd);
        String errcode = loginInfoJson.getString("errcode");
        if (StringUtils.isNullOrEmpty(errcode))
        {
            // 用户信息
            ProfileVo profile = profileMnt.profileLogin(session, loginInfoJson);
            loginInfoJson.put(FieldName.PROFILE_INFO, profile.toJson());

            // 买家信息
            BuyerVo buyer = getBuyerInfo(channelId);
            if (buyer == null)
            {
                buyer = new BuyerVo(profile.getId());
                buyer.fromDB(db);
                buyerMap.put(channelId, buyer);
            }
            if (buyer.isEmpty())
            {//买家第一次登陆
                //生成买家Id
                buyer.setId(Long.toString(IdGenerator.getInstance().nextId()));
                buyer.setRegisterTime(nowTime);
            }
            buyer.writeDB(db);
            loginInfoJson.put(FieldName.BUYER_INFO, buyer.toJson());

            recordMnt.addBuyerLoginRecord(buyer);
        }
        rspdClient(session, loginInfoJson, ServerName.CLIENT_MP);
    }

    public BuyerVo getBuyerInfo(String channelId)
    {
        return buyerMap.get(channelId);
    }
}
