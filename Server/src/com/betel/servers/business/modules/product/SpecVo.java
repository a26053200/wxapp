package com.betel.servers.business.modules.product;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: 商品规格信息
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/7 23:45
 */
public class SpecVo extends AttributeVo
{

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
