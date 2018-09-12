package com.betel.servers.gate;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @ClassName: GateClientDecoder
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/6/1 21:00
 */
public class GateClientDecoder extends ByteArrayDecoder
{
    final static Logger logger = Logger.getLogger(GateClientDecoder.class);

    GateMonitor monitor;

    public GateClientDecoder(GateMonitor monitor)
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
