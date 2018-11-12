package com.betel.servers.balance;

import com.betel.common.Monitor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.log4j.Logger;

/**
 * @ClassName: BalanceServerHandler
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/9 0:33
 */
public class BalanceServerHandler extends SimpleChannelInboundHandler<ByteBuf>
{
    final static Logger logger = Logger.getLogger(BalanceServerHandler.class.getName());

    protected BalanceMonitor monitor;

    public BalanceServerHandler(BalanceMonitor monitor)
    {
        this.monitor = monitor;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {  // (2)
        Channel incoming = ctx.channel();
        logger.info("Client ip:" + incoming.remoteAddress() + " is added");
        // Broadcast a message to multiple Channels
        // channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");

        monitor.getChannelGroup().add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception
    {
        Channel incoming = ctx.channel();
        logger.info("收到数据长度:" + buf.readableBytes());
        monitor.recvByteBuf(ctx,buf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        super.channelReadComplete(ctx);
        Channel incoming = ctx.channel();
        logger.info("Client ip:" + incoming.remoteAddress() + " channel has flush over");
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    { // (5)
        Channel incoming = ctx.channel();
        logger.info("Client ip:" + incoming.remoteAddress() + " is online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    { // (6)
        Channel incoming = ctx.channel();
//        String ra1 = incoming.remoteAddress().toString();
//        String ra2 = monitor.getBusinessServerContext().channel().remoteAddress().toString();
//        if(ra1.equals(ra2))
//        {
//            logger.info("BusinessServer Client ip:" + incoming.remoteAddress() + " is offline");
//        }else{
//            logger.info("Client ip:" + incoming.remoteAddress() + " is offline");
//            monitor.notifyGameServerClientOffline(ctx);//通知游戏服务器:玩家客户端已经离线
//        }
        monitor.getChannelGroup().remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        Channel incoming = ctx.channel();
        logger.error("Client ip:" + incoming.remoteAddress() + " is exception");
        // 当出现异常就关闭连接
        //cause.printStackTrace();
        logger.info(String.format("[GateServer] 远程IP:%s 的链接出现异常,其通道即将关闭", incoming.remoteAddress()));
        ctx.close().addListener(new GenericFutureListener<Future<? super Void>>()
        {
            @Override
            public void operationComplete(Future<? super Void> future)
                    throws Exception
            {
                if (future.isSuccess())
                    logger.info("异常关闭成功");
                else
                    logger.info("异常关闭失败");
            }
        });
    }
}
