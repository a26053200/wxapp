package com.betel.servers.business.modules.record;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName: RecordMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/20 22:07
 */
public class RecordMnt extends SubMonitor
{
    public RecordMnt(Monitor base)
    {
        super(base);
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction)
    {

    }
}
