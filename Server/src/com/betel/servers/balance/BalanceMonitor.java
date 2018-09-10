package com.betel.servers.balance;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

/**
 * @ClassName: BalanceMonitor
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/8 22:47
 */
public class BalanceMonitor extends Monitor
{
    final static Logger logger = Logger.getLogger(BalanceMonitor.class);

    @Override
    protected void initDB()
    {
    }//负载均衡目前不需要数据库

    // 业务服务器上下文
    private ChannelHandlerContext businessServerContext;
    // 业务服务器上下文
    public ChannelHandlerContext getBusinessServerContext()
    {
        return businessServerContext;
    }
    @Override
    protected void RespondJson(ChannelHandlerContext ctx, JSONObject jsonObject)
    {
        String server = jsonObject.get(FieldName.SERVER).toString();
        switch (server)
        {
            //均衡服务器的消息直接处理
            case ServerName.BALANCE_SERVER:
            {
                String action = jsonObject.getString(Action.NAME);
                switch (action)
                {
                    case Action.HANDSHAKE_BUSINESS2BALANCE://业务服务器和均衡服务器握手成功
                        businessServerContext = ctx;
                        logger.info("The business server and balance server shook hands successfully.");
                        break;
                }
            }
            break;
        }
    }
}
