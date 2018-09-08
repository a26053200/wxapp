package com.betel.servers.gate;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.consts.FieldName;
import com.betel.servers.balance.BalanceServer;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

/**
 * @ClassName: GateMonitor
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/6/8 23:01
 */
public class GateMonitor extends Monitor
{
    final static Logger logger = Logger.getLogger(GateMonitor.class);

    public GateMonitor()
    {
        super();
    }
    private BalanceServer balanceServer;

    private ChannelHandlerContext gameServerContext;

    public ChannelHandlerContext getGameServerContext()
    {
        return gameServerContext;
    }

    public void SetBalanceServer(BalanceServer balanceServer)
    {
        this.balanceServer = balanceServer;
    }


    @Override
    protected void initDB(){ }//网关服务器不需要数据库

    @Override
    protected void RespondJson(ChannelHandlerContext ctx, JSONObject jsonObject)
    {
        String server = jsonObject.get(FieldName.SERVER).toString();
        switch (server)
        {

        }
    }

    //转发给游戏服务器
    private void forwardToGameServer(JSONObject jsonObject)
    {

    }

    //直接转发给客户端
    private void forwardToClient(ChannelHandlerContext clientCtx, JSONObject jsonObject)
    {

    }

    //直接转发给客户端
    public void notifyGameServerClientOffline(ChannelHandlerContext ctx)
    {

    }
}
