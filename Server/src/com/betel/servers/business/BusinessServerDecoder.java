package com.betel.servers.business;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @ClassName: GateServerDecoder
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/6/1 21:00
 */
public class BusinessServerDecoder extends ByteArrayDecoder
{
    final static Logger logger = Logger.getLogger(BusinessServerDecoder.class);

    BusinessMonitor monitor;

    public BusinessServerDecoder(BusinessMonitor monitor)
    {
        this.monitor = monitor;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception
    {
        super.decode(ctx, msg, out);
        monitor.recvJsonBuff(ctx,msg);
    }
}
