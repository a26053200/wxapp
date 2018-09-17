package com.betel.common;

import com.alibaba.fastjson.JSONObject;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.session.Session;
import com.betel.utils.BytesUtils;
import io.netty.channel.ChannelHandlerContext;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: SubMonitor
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/8 22:51
 */
public abstract class SubMonitor
{
    protected Monitor base;

    protected Jedis db;

    public SubMonitor(Monitor base)
    {
        this.base = base;
        this.db = base.db;
    }

    public abstract void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction);

    //发送给网关
    protected void send2Gate(ChannelHandlerContext ctx, String msg)
    {
        ctx.channel().writeAndFlush(BytesUtils.packBytes(BytesUtils.string2Bytes(msg)));
    }
    //回应客户端请求
    protected void rspdClient(Session session)
    {
        rspdClient(session, null);
    }

    //回应客户端请求 带数据体 (先转发给网关服务器,再由网关服务器转发给客户端)
    protected void rspdClient(Session session, JSONObject sendJson)
    {
        String channelId = session.getChannelId();
        JSONObject rspdJson = new JSONObject();
        rspdJson.put(FieldName.SERVER, ServerName.GATE_SERVER);
        rspdJson.put(FieldName.FORWARD_SERVER, ServerName.CLIENT);
        rspdJson.put(Action.NAME, session.getRqstAction());
        rspdJson.put(FieldName.CHANNEL_ID, channelId);
        rspdJson.put(FieldName.STATE, session.getState().ordinal());
        if(sendJson != null)
            rspdJson.put(FieldName.DATA, sendJson);
        send2Gate(session.getContext(), rspdJson.toString());
    }
}
