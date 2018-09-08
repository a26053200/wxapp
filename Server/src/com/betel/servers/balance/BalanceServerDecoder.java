package com.betel.servers.balance;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @ClassName: BalanceServerDecoder
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/9 0:27
 */
public class BalanceServerDecoder extends ByteArrayDecoder
{
    final static Logger logger = Logger.getLogger(BalanceServerDecoder.class);

    BalanceMonitor monitor;

    public BalanceServerDecoder(BalanceMonitor monitor)
    {
        this.monitor = monitor;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception
    {
        super.decode(ctx, msg, out);
        monitor.recvByteBuf(ctx,msg);
    }
}
