package com.betel.servers.gate;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.servers.balance.BalanceServer;
import com.betel.utils.BytesUtils;
import io.netty.buffer.Unpooled;
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


    public void SetBalanceServer(BalanceServer balanceServer)
    {
        this.balanceServer = balanceServer;
    }


    @Override
    protected void initDB(){ }//网关服务器不需要数据库

    @Override
    protected void RespondJson(ChannelHandlerContext ctx, JSONObject jsonObject)
    {

        String action = jsonObject.get("action").toString();

        switch (action)
        {
            case Action.MP_GET_PROFILE:
                //login(ctx, jsonObject);
                httpResponse(ctx,"测试HTTP请求");
                break;
        }
    }

    private void httpResponse(ChannelHandlerContext ctx, String msg)
    {
        //msg = StringUtils.AddControlChar(msg);
        //msg = StringUtils.AddControlChar(msg);
        logger.info(String.format("[Rspd]:%s", msg));
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, OK, Unpooled.wrappedBuffer(BytesUtils.string2Bytes(msg)));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.write(response);
    }
}
