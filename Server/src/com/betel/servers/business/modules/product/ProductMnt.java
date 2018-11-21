package com.betel.servers.business.modules.product;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.consts.Action;
import com.betel.session.Session;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

/**
 * @ClassName: ProductMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/12 0:16
 */
public class ProductMnt extends SubMonitor
{
    final static Logger logger = Logger.getLogger(ProductMnt.class);

    public ProductMnt(Monitor base)
    {
        super(base);
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction)
    {
        Session session = new Session(ctx, jsonObject);
        switch (subAction)
        {
            case Action.NONE:
            default:
                break;
        }
    }
}
