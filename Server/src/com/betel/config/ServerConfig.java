package com.betel.config;

import com.betel.consts.ServerName;

import java.util.HashMap;

/**
 * @ClassName: ServerConfig
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/13 1:09
 */
public class ServerConfig
{
    private static HashMap<String, ServerConfigVo> s_cfgMap;

    /**
     * 获取服务器配置
     * @param serverName
     * @return
     */
    public static ServerConfigVo getServerConfig(String serverName)
    {
        if(s_cfgMap == null)
        {
            s_cfgMap = new HashMap<>();
            s_cfgMap.put(ServerName.GATE_SERVER,new ServerConfigVo(){
                public ServerConfigVo()
                {

                }
            });
        }
        return s_cfgMap.get(serverName);
    }
}
