package com.betel.servers.business;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.database.RedisClient;
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
        db = RedisClient.getInstance().getDB(2);//获取数据库
    }

    @Override
    protected void RespondJson(ChannelHandlerContext ctx, JSONObject jsonObject)
    {
        String[] actions = jsonObject.get("action").toString().split("@");
        String moduleName = actions[0];
        String subAction = actions.length > 1 ? actions[1] : Action.NONE;
        logger.info("Recv action: " + jsonObject.get("action"));
        SubMonitor subMnt = subMonitorMap.get(moduleName);
        if (subMnt != null)
            subMnt.ActionHandler(ctx, jsonObject, subAction);
        else
            logger.error("There is no monitor for action:" + jsonObject.get("action"));
    }
}
