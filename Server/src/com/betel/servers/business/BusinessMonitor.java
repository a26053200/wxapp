package com.betel.servers.business;

import com.alibaba.fastjson.JSONObject;
import com.betel.asd.BaseAction;
import com.betel.common.Monitor;
import com.betel.consts.*;
import com.betel.database.RedisClient;
import com.betel.servers.business.modules.brand.BrandAction;
import com.betel.servers.business.modules.buyer.BuyerAction;
import com.betel.servers.business.modules.profile.ProfileAction;
import com.betel.servers.business.modules.record.RecordAction;
import com.betel.servers.business.modules.seller.SellerAction;
import com.betel.utils.BytesUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

/**
 * @ClassName: GateMonitor
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/6/8 23:01
 */
public class BusinessMonitor extends Monitor
{
    final static Logger logger = Logger.getLogger(BusinessMonitor.class);

    private BusinessClient businessServerClient;

    public BusinessMonitor()
    {
        super();
        InitSubMonitors();

        actionMap.put(Bean.RECORD,      new RecordAction(this));
        actionMap.put(Bean.PROFILE,     new ProfileAction(this));
        actionMap.put(Bean.BUYER,       new BuyerAction(this));
        actionMap.put(Bean.SELLER,      new SellerAction(this));
        actionMap.put(Bean.BRAND,       new BrandAction(this));
    }
    public void SetGameServerClient(BusinessClient businessServerClient)
    {
        this.businessServerClient = businessServerClient;
    }

    public void handshake(Channel channel)
    {
        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.SERVER, ServerName.BALANCE_SERVER);
        sendJson.put(Action.NAME, Action.HANDSHAKE_BUSINESS2BALANCE);
        channel.writeAndFlush(BytesUtils.packBytes(BytesUtils.string2Bytes(sendJson.toString())));
    }

    @Override
    protected void initDB()
    {
        db = RedisClient.getInstance().getDB(2);//初始化redis数据库
    }

    @Override
    protected void RespondJson(ChannelHandlerContext ctx, JSONObject jsonObject)
    {
        String actionParam = jsonObject.getString("action");
        String[] actions = actionParam.split("@");
        logger.info("Recv action: " + actionParam);
        String actionName = actions[0];
        String actionMethod = actions.length > 1 ? actions[1] : Action.NONE;
        BaseAction action = actionMap.get(actionName);
        if (action != null)
            action.ActionHandler(ctx, jsonObject, actionMethod);
        else
        {
            logger.error("There is no action service for action:" + actionParam);
        }

    }
}
