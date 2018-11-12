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
    private String recorderId;      //记录者OpenId
    private String addTime;         //记录添加时间

    public RecordVo(String recorderId, String id, String type)
    {
        super(RedisKeys.record + ":" + recorderId + ":" + type + ":" + id);
        this.recorderId = recorderId;
        this.id = id;
        this.type = type;
    }
    public String getId()
    {
        return id;
    }

    public String getRecorderId()
    {
        return recorderId;
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
        db.hset(primaryKey, RedisKeys.record_profile_id, recorderId);
        db.hset(primaryKey, RedisKeys.record_type,          type);
        db.hset(primaryKey, RedisKeys.record_content,       content);
        db.hset(primaryKey, RedisKeys.record_add_time,      addTime);
    }
}
