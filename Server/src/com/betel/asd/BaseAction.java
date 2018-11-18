package com.betel.asd;


import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.session.Session;
import com.betel.utils.BytesUtils;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName: BaseAction
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 0:23
 */
public class BaseAction<T>
{
    protected Monitor monitor;

    protected BaseService<T> service;

    public Monitor getMonitor()
    {
        return monitor;
    }

    public BaseService<T> getService()
    {
        return service;
    }

    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction)
    {

    }

    //发送给网关
    protected void send2Gate(ChannelHandlerContext ctx, String msg)
    {
        ctx.channel().writeAndFlush(BytesUtils.packBytes(BytesUtils.string2Bytes(msg)));
    }
    //回应客户端请求
//    protected void rspdClient(Session session)
//    {
//        rspdClient(session, null);
//    }

    //回应客户端请求 带数据体 (先转发给网关服务器,再由网关服务器转发给客户端)
    protected void rspdClient(Session session, JSONObject sendJson, String forwardServer)
    {
        String channelId = session.getChannelId();
        JSONObject rspdJson = new JSONObject();
        rspdJson.put(FieldName.SERVER, ServerName.GATE_SERVER);
        rspdJson.put(FieldName.FORWARD_SERVER, forwardServer);
        rspdJson.put(Action.NAME, session.getRqstAction());
        rspdJson.put(FieldName.CHANNEL_ID, channelId);
        rspdJson.put(FieldName.STATE, session.getState().ordinal());
        if(sendJson != null)
            rspdJson.put(FieldName.DATA, sendJson);
        send2Gate(session.getContext(), rspdJson.toString());
    }
}
