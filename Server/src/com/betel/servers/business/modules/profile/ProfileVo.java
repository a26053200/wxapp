package com.betel.servers.business.modules.profile;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import com.betel.database.RedisKeys;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: 用户信息（包括买家和卖家和管理员）
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/8 0:35
 */
public class ProfileVo extends BaseVo implements IDataBaseVo
{
    private String id;
    private String openId;
    private String unionId;
    private String registerTime;    //买家第一次登陆游戏时间,即买家在该服务器的注册时间
    // 微信用户信息
    private String wxNickName;
    private String wxGender;
    private String wxLanguage;
    private String wxCity;
    private String wxProvince;
    private String wxCountry;
    private String wxAvatarUrl;

    public ProfileVo(String openId)
    {
        super(RedisKeys.profile + ":" + openId);
        this.openId = openId;
    }
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOpenId()
    {
        return openId;
    }

    public String getUnionId()
    {
        return unionId;
    }

    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }

    public String getRegisterTime()
    {
        return registerTime;
    }

    public void setRegisterTime(String registerTime)
    {
        this.registerTime = registerTime;
    }

    public String getWxNickName()
    {
        return wxNickName;
    }

    public String getWxGender()
    {
        return wxGender;
    }

    public String getWxLanguage()
    {
        return wxLanguage;
    }

    public String getWxCity()
    {
        return wxCity;
    }

    public String getWxProvince()
    {
        return wxProvince;
    }

    public String getWxAvatarUrl()
    {
        return wxAvatarUrl;
    }

    public void setWxUserInfo(JSONObject wxUserInfo)
    {
        this.wxNickName     = wxUserInfo.getString("nickName");
        this.wxGender       = wxUserInfo.getString("gender");
        this.wxLanguage     = wxUserInfo.getString("language");
        this.wxCity         = wxUserInfo.getString("city");
        this.wxProvince     = wxUserInfo.getString("province");
        this.wxCountry      = wxUserInfo.getString("country");
        this.wxAvatarUrl    = wxUserInfo.getString("avatarUrl");
    }

    @Override
    public void fromDB(Jedis db)
    {
        this.db = db;
        if (db.hgetAll(primaryKey).isEmpty())
            isEmpty = true;
        else
        {
            id              = db.hget(primaryKey, RedisKeys.profile_id);
            openId          = db.hget(primaryKey, RedisKeys.profile_open_id);
            unionId         = db.hget(primaryKey, RedisKeys.profile_union_id);
            registerTime    = db.hget(primaryKey, RedisKeys.profile_register_time);
        }
    }

    @Override
    public void writeDB(Jedis db)
    {
        isEmpty = false;
        db.hset(primaryKey, RedisKeys.profile_id,             id);
        db.hset(primaryKey, RedisKeys.profile_open_id,        openId);
        db.hset(primaryKey, RedisKeys.profile_register_time,  registerTime);

        db.hset(primaryKey, RedisKeys.profile_wx_nickname,    wxNickName);
        db.hset(primaryKey, RedisKeys.profile_wx_gender,      wxGender);
        db.hset(primaryKey, RedisKeys.profile_wx_language,    wxLanguage);
        db.hset(primaryKey, RedisKeys.profile_wx_city,        wxCity);
        db.hset(primaryKey, RedisKeys.profile_wx_province,    wxProvince);
        db.hset(primaryKey, RedisKeys.profile_wx_country,     wxCountry);
        db.hset(primaryKey, RedisKeys.profile_wx_avatar_url,  wxAvatarUrl);

        if(unionId != null)
            db.hset(primaryKey, RedisKeys.profile_union_id,       unionId);
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        json.put(RedisKeys.profile_id, id);
        json.put(RedisKeys.profile_open_id, openId);
        return json;
    }
}
