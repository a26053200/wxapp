package com.betel.servers.business.modules.product;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: AttributeVo
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/15 22:32
 */
public class AttributeVo extends BaseVo implements IDataBaseVo
{
    protected String id;
    protected String name;
    protected String addTime;    //添加时间
    protected String updateTime;    //添加时间

    public AttributeVo()
{

}
    public AttributeVo(String key,String id)
    {
        super(key + ":" + id);
        this.id = id;
    }
    public String getId()
    {
        return id;
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

    }
}
