package com.betel.servers.business.modules.product;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: BrandVo
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/7 22:54
 */
public class BrandVo extends BaseVo implements IDataBaseVo
{
    private final static String ID = "id";

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
