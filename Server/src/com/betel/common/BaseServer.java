package com.betel.common;

import com.betel.database.RedisClient;
import com.betel.utils.IdGenerator;
import com.betel.utils.NetUtils;
import org.apache.log4j.Logger;

/**
 * @ClassName: BaseServer
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/8 22:39
 */
public abstract class BaseServer
{
    private Logger logger = Logger.getLogger(BaseServer.class);

    protected int port;

    protected String serverName;

    protected String localHost;

    public BaseServer(String serverName, int port)
    {
        this.serverName = serverName;
        this.port = port;
        this.localHost = NetUtils.GetLocalHostAddress();
        logger.info("[" + serverName + "] start " + this.localHost + ":" + this.port);
        //连接数据库
        RedisClient.getInstance().connectDB("127.0.0.1");
        //Id生成器
        IdGenerator.init(Thread.currentThread().getId());
    }

    public abstract void run() throws Exception;
}
