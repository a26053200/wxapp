package com.betel.servers.business.modules.product;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import com.betel.database.RedisKeys;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: BrandVo
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/7 22:54
 */
public class BrandVo extends BaseVo implements IDataBaseVo
{
    protected String id;
    protected String name;
    protected String addTime;    //添加时间
    protected String updateTime;    //添加时间

    @Override
    public String getPrimaryKey()
    {
        return primaryKey;
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
        primaryKey = RedisKeys.brand + RedisKeys.SPLIT + id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddTime()
    {
        return addTime;
    }

    public void setAddTime(String addTime)
    {
        this.addTime = addTime;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

    @Override
    public void fromDB(Jedis db)
    {
        if (db.hgetAll(primaryKey).isEmpty())
            isEmpty = true;
        else
        {
            id              = db.hget(primaryKey, RedisKeys.brand_id);
            name            = db.hget(primaryKey, RedisKeys.brand_name);
            addTime         = db.hget(primaryKey, RedisKeys.brand_add_time);
            updateTime      = db.hget(primaryKey, RedisKeys.brand_update_time);
        }
    }

    @Override
    public void writeDB(Jedis db)
    {
        isEmpty = false;
        db.hset(primaryKey, RedisKeys.brand_id,             id);
        db.hset(primaryKey, RedisKeys.brand_name,           name);
        db.hset(primaryKey, RedisKeys.brand_add_time,       addTime);
        db.hset(primaryKey, RedisKeys.brand_update_time,    updateTime);
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        json.put(RedisKeys.brand_id,    id);
        json.put(RedisKeys.brand_name,  name);
        return json;
    }

    public static List<BrandVo> listFromDB(Jedis db)
    {
        List<BrandVo> list = new ArrayList<>();
        Set<String> profileSet = db.keys(RedisKeys.brand + ":*");
        Iterator<String> it = profileSet.iterator();
        while (it.hasNext())
        {
            String primaryKey = it.next();
            BrandVo vo = new BrandVo();
            vo.primaryKey = primaryKey;
            vo.fromDB(db);
            list.add(vo);
        }
        return  list;
    }
}
