package com.betel.servers.balance;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.consts.Action;
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

    @Override
    protected void RespondJson(ChannelHandlerContext ctx, JSONObject jsonObject)
    {
        String action = jsonObject.get("action").toString();

        switch (action)
        {
            case Action.LOGIN_ACCOUNT:
                //login(ctx, jsonObject);
                break;
        }
    }


}
