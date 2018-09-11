package com.betel.servers.business.modules.product;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName: ProductMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/12 0:16
 */
public class ProductMnt extends SubMonitor
{
    public ProductMnt(Monitor base)
    {
        super(base);
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction)
    {

    }
}
