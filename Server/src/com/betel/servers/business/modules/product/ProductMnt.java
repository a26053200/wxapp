package com.betel.servers.business.modules.product;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.servers.business.modules.brand.BrandAction;
import com.betel.session.Session;
import com.betel.utils.IdGenerator;
import com.betel.utils.TimeUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

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
