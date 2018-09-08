package com.betel.servers.balance;

import com.betel.utils.BytesUtils;
import com.betel.utils.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.log4j.Logger;

/**
 * @ClassName: BalanceServerInboundHandler
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/8 22:44
 */
public class BalanceServerInboundHandler extends ChannelInboundHandlerAdapter
{
    final static Logger logger = Logger.getLogger(BalanceServerInboundHandler.class.getName());

    private BalanceMonitor monitor;

    private HttpRequest request = null;

    public BalanceServerInboundHandler(BalanceMonitor monitor)
    {
        this.monitor = monitor;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception
    {
        String res = "";
        if (msg instanceof HttpRequest)
        {
            request = (HttpRequest) msg;
            String uri = request.uri();
            try
            {
                res = uri.substring(1);
                if (!StringUtils.isNullOrEmpty(res))
                {
                    logger.info("[recv]" + res);
                    monitor.recvJson(ctx, res);
                }
            }
            catch (Exception e)
            {//处理出错，返回错误信息
                e.printStackTrace();
                responseError(ctx, "Balance Server Error");
            }
        }
        if (msg instanceof HttpContent)
        {
            try
            {
                HttpContent content = (HttpContent) msg;
                ByteBuf buf = content.content();
                res = BytesUtils.readString(buf);
                buf.release();
                if (StringUtils.isNullOrEmpty(res))
                    responseError(ctx, "Http request data is Empty!");
                else
                {
                    logger.info("[recv]" + res);
                    monitor.recvJson(ctx, res);
                }
            }
            catch (Exception e)
            {//处理出错，返回错误信息
                e.printStackTrace();
                responseError(ctx,"Balance SERVER Error");
            }
        }
    }

    private void responseError(ChannelHandlerContext ctx, String errorMsg)
    {
        logger.error(errorMsg);
        ctx.channel().write(BytesUtils.string2Bytes(errorMsg));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        //logger.info("http server channelReadComplete..");
        ctx.flush();//刷新后才将数据发出到SocketChannel
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception
    {
        logger.error("http server exceptionCaught..");
        ctx.close();
    }
}
