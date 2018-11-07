package com.betel.servers.business.modules.record;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import com.betel.database.RedisKeys;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: 登陆记录
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/20 22:00
 */
public class RecordVo extends BaseVo implements IDataBaseVo
{
    private String id;
    private String type;            //记录类型
    private String content;         //记录内容
    private String profileId;  //记录者OpenId
    private String addTime;         //记录添加时间

    public RecordVo(String profileId, String id)
    {
        super(RedisKeys.record + ":" + profileId + ":" + id);
        this.profileId = profileId;
        this.id = id;
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

    public String getAddTime()
    {
        return addTime;
    }

    public void setAddTime(String addTime)
    {
        this.addTime = addTime;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    public JSONObject toJson()
    {
        return null;
    }

    @Override
    public void fromDB(Jedis db)
    {

    }

    @Override
    public void writeDB(Jedis db)
    {
        db.hset(primaryKey, RedisKeys.record_id,            id);
        db.hset(primaryKey, RedisKeys.record_profile_id, profileId);
        db.hset(primaryKey, RedisKeys.record_type,          type);
        db.hset(primaryKey, RedisKeys.record_content,       content);
        db.hset(primaryKey, RedisKeys.record_add_time,      addTime);
    }
}
