package com.betel.servers.business.modules.buyer;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import com.betel.database.RedisKeys;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: BuyerVo
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/13 0:21
 */
public class BuyerVo extends BaseVo implements IDataBaseVo
{
    private String id;
    private String profileId;
    private String registerTime;    //买家第一次登陆游戏时间,即买家在该服务器的注册时间

    public BuyerVo(String profileId)
    {
        super(RedisKeys.buyer + ":" + profileId);
        this.profileId = profileId;
    }
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProfileId()
    {
        return profileId;
    }

    public void setProfileId(String profileId)
    {
        this.profileId = profileId;
    }

    public String getRegisterTime()
    {
        return registerTime;
    }

    public void setRegisterTime(String registerTime)
    {
        this.registerTime = registerTime;
    }

    @Override
    public void fromDB(Jedis db)
    {
        this.db = db;
        if (db.hgetAll(primaryKey).isEmpty())
            isEmpty = true;
        else
        {
            id              = db.hget(primaryKey, RedisKeys.buyer_id);
            profileId       = db.hget(primaryKey, RedisKeys.buyer_profile_id);
            registerTime    = db.hget(primaryKey, RedisKeys.buyer_register_time);
        }
    }

    @Override
    public void writeDB(Jedis db)
    {
        isEmpty = false;
        db.hset(primaryKey, RedisKeys.buyer_id,             id);
        db.hset(primaryKey, RedisKeys.buyer_profile_id,     profileId);
        db.hset(primaryKey, RedisKeys.buyer_register_time,  registerTime);
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        json.put(RedisKeys.buyer_id, id);
        json.put(RedisKeys.buyer_profile_id, profileId);
        return json;
    }
}
