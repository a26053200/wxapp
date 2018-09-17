package com.betel.servers.gate;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.servers.balance.BalanceServer;
import com.betel.servers.business.BusinessClient;
import com.betel.utils.BytesUtils;
import com.betel.utils.StringUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import org.apache.log4j.Logger;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @ClassName: GateMonitor
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/6/8 23:01
 */
public class GateMonitor extends Monitor
{
    final static Logger logger = Logger.getLogger(GateMonitor.class);

    public GateMonitor()
    {
        super();
    }
    private BalanceServer balanceServer;

    private GateClient gateClient;

    public void SetBalanceServer(BalanceServer balanceServer)
    {
        this.balanceServer = balanceServer;
    }
    public void SetGateServerClient(GateClient gateClient)
    {
        this.gateClient = gateClient;
    }

    public void handshake(Channel channel)
    {
        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.SERVER, ServerName.BALANCE_SERVER);
        sendJson.put(Action.NAME, Action.HANDSHAKE_GATE2BALANCE);
        String jsonString = sendJson.toString();
        channel.writeAndFlush(BytesUtils.packBytes(BytesUtils.string2Bytes(jsonString)));
    }

    @Override
    protected void initDB(){ }//网关服务器不需要数据库

    @Override
    protected void RespondJson(ChannelHandlerContext ctx, JSONObject jsonObject)
    {
        String server = jsonObject.get(FieldName.SERVER).toString();
        switch (server)
        {
            // 转发给均衡服务器
            case ServerName.BUSINESS_SERVER:
                jsonObject.put(FieldName.CHANNEL_ID, ctx.channel().id().asLongText());
                regContext(ctx);
                forward2BalanceServer(jsonObject);
                break;
            // 转发给客户端
            case ServerName.CLIENT:
                String channelId = jsonObject.getString(FieldName.CHANNEL_ID);
                jsonObject.remove(FieldName.CHANNEL_ID);
                jsonObject.remove(FieldName.SERVER);
                ChannelHandlerContext clientCtx = getContext(channelId);
                if (clientCtx != null)
                {
                    httpResponse(clientCtx, jsonObject.toString());
                    delContext(clientCtx);
                }
                else
                    logger.info("Client has not ChannelHandlerContext");
                break;
            default:
                httpResponse(ctx,"该请求未知服务器");
                break;
        }
    }

    //直接响应客户端
    private void httpResponse(ChannelHandlerContext ctx, String msg)
    {
        logger.info(String.format("[Rspd]:%s", msg));
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, OK, Unpooled.wrappedBuffer(BytesUtils.string2Bytes(msg)));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.writeAndFlush(response);
    }

    // 转发给均衡服务器
    private void forward2BalanceServer(JSONObject jsonObject)
    {
        byte[] bytes = BytesUtils.string2Bytes(jsonObject.toString());
        gateClient.GetChanel().writeAndFlush(bytes);
    }
}
