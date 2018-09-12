package com.betel.servers.business.modules.buyer;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.consts.Action;
import com.betel.session.Session;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName: BuyerMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/13 0:21
 */
public class BuyerMnt extends SubMonitor
{
    public BuyerMnt(Monitor base)
    {
        super(base);
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction)
    {
        Session session = new Session(ctx, jsonObject);
        switch (subAction)
        {
            case Action.NONE:

                break;
            case Action.MP_LOGIN_WX_SERVER:
                login_wx_server(session);
                break;
            default:
                break;
        }
    }

    private void login_wx_server(Session session)
    {

    }
}
