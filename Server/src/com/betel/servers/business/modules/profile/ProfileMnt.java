package com.betel.servers.business.modules.profile;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.consts.FieldName;
import com.betel.session.Session;
import com.betel.utils.IdGenerator;
import com.betel.utils.StringUtils;
import com.betel.utils.TimeUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;

/**
 * @ClassName: ProfileMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/8 0:57
 */
public class ProfileMnt extends SubMonitor
{
    final static Logger logger = Logger.getLogger(ProfileMnt.class);

    private final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    private final String WX_LOGIN_PARAM = "appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    private final String APP_ID = "wx4bf12d9a5d200dfb";

    private final String APP_SECRET = "94d170fe988568d97d3cb5e05ee42cfd";

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

    // 微信用户登录
    public ProfileVo profileLogin(Session session, JSONObject wxLoginInfoJson)
    {
        String nowTime = TimeUtils.date2String(new Date());
        String channelId = session.getChannelId();
        String openid = wxLoginInfoJson.getString(FieldName.OPEN_ID);// 微信OpenID
        ProfileVo profile = getProfileInfo(channelId);// 用户信息
        if (profile == null)
        {
            profile = new ProfileVo(openid);
            profile.fromDB(db);
            String unionId = wxLoginInfoJson.getString("unionId");
            if (!StringUtils.isNullOrEmpty(unionId))
                profile.setUnionId(unionId);
            addProfileInfo(channelId, profile);
        }
        if (profile.isEmpty())
        {//买家第一次登陆
            //生成用户Id
            profile.setId(Long.toString(IdGenerator.getInstance().nextId()));
            profile.setRegisterTime(nowTime);
        }
        profile.setWxUserInfo(session.getRecvJson().getJSONObject("wxUserInfo"));
        profile.writeDB(db);
        return profile;
    }

    public ProfileVo addProfileInfo(String channelId, ProfileVo info)
    {
        return profileMap.put(channelId, info);
    }

    public ProfileVo getProfileInfo(String channelId)
    {
        return profileMap.get(channelId);
    }
}
