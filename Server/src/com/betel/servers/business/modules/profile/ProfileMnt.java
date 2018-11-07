package com.betel.servers.business.modules.profile;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.servers.business.modules.buyer.BuyerVo;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

/**
 * @ClassName: ProfileMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/8 0:57
 */
public class ProfileMnt extends SubMonitor
{
    private HashMap<String, ProfileVo> profileMap;

    public ProfileMnt(Monitor base)
    {
        super(base);
        profileMap = new HashMap<>();
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction)
    {

    }

    public ProfileVo addProfileInfo(String channelId, ProfileVo info)
    {
        return profileMap.put(channelId,info);
    }

    public ProfileVo getProfileInfo(String channelId)
    {
        return profileMap.get(channelId);
    }
}
