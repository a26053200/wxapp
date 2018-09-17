package com.betel.servers.business.modules.buyer;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.session.Session;
import com.betel.utils.HttpRequest;
import com.betel.utils.StringUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

/**
 * @ClassName: BuyerMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/13 0:21
 */
public class BuyerMnt extends SubMonitor
{
    final static Logger logger = Logger.getLogger(BuyerMnt.class);

    public BuyerMnt(Monitor base)
    {
        super(base);
    }

    private final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    private final String WX_LOGIN_PARAM = "appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    private final String APP_ID = "wx4bf12d9a5d200dfb";

    private final String APP_SECRET = "94d170fe988568d97d3cb5e05ee42cfd";
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
        rspdClient(session,sendJson);
    }
    private void login_wx_server(Session session)
    {
        String code = session.getRecvJson().getString("code");
        String wxApiRspd = HttpRequest.sendPost(WX_LOGIN_URL,String.format(WX_LOGIN_PARAM,APP_ID,APP_SECRET,code));
        logger.info(wxApiRspd);
        JSONObject wxApi = JSONObject.parseObject(wxApiRspd);
        rspdClient(session,wxApi);
    }
}
