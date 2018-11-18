package com.betel.servers.business.modules.profile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.betel.asd.BaseAction;
import com.betel.common.Monitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.servers.business.modules.beans.Brand;
import com.betel.servers.business.modules.beans.Profile;
import com.betel.session.Session;
import com.betel.utils.IdGenerator;
import com.betel.utils.JsonUtils;
import com.betel.utils.StringUtils;
import com.betel.utils.TimeUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @ClassName: BuyerAction
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 23:00
 */
public class ProfileAction extends BaseAction<Profile>
{
    final static Logger logger = Logger.getLogger(ProfileAction.class);

    private HashMap<String, Profile> profileMap;

    public ProfileAction(Monitor monitor)
    {
        this.monitor = monitor;
        this.service = new ProfileService();
        this.service.setBaseDao(new ProfileDao(monitor.getDB()));

        profileMap = new HashMap<>();
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String method)
    {
        Session session = new Session(ctx, jsonObject);
        switch (method)
        {
            case Action.NONE:
                break;
            default:
                break;
        }
    }

    // 微信用户登录
    public Profile profileLogin(Session session, JSONObject wxLoginInfoJson)
    {
        String nowTime = TimeUtils.date2String(new Date());
        String channelId = session.getChannelId();
        String openid = wxLoginInfoJson.getString(FieldName.OPEN_ID);// 微信OpenID
        Profile profile = getProfileInfo(channelId);// 用户信息
        if (profile == null)
        {
            profile = service.getEntryById(openid);
            if (profile == null)
            {//买家第一次登陆
                //生成用户Id
                //profile.setId(Long.toString(IdGenerator.getInstance().nextId()));
                profile = new Profile();
                profile.setId(openid);
                setWxUserInfo(profile,session.getRecvJson().getJSONObject("wxUserInfo"));
                profile.setRegisterTime(nowTime);
                service.addEntry(profile);
            }
            String unionId = wxLoginInfoJson.getString("unionId");
            if (!StringUtils.isNullOrEmpty(unionId))
                profile.setUnionId(unionId);
            addProfileInfo(channelId, profile);
        }else{
            //更新微信信息
            setWxUserInfo(profile,session.getRecvJson().getJSONObject("wxUserInfo"));
            service.addEntry(profile);
        }
        return profile;
    }

    private void setWxUserInfo(Profile profile, JSONObject wxUserInfo)
    {
        profile.setWxNickName(wxUserInfo.getString("nickName"));
        profile.setWxGender(wxUserInfo.getString("gender"));
        profile.setWxLanguage(wxUserInfo.getString("language"));
        profile.setWxCity(wxUserInfo.getString("city"));
        profile.setWxProvince(wxUserInfo.getString("province"));
        profile.setWxCountry(wxUserInfo.getString("country"));
        profile.setWxAvatarUrl(wxUserInfo.getString("avatarUrl"));
    }

    public Profile addProfileInfo(String channelId, Profile info)
    {
        return profileMap.put(channelId, info);
    }

    public Profile getProfileInfo(String channelId)
    {
        return profileMap.get(channelId);
    }

}
