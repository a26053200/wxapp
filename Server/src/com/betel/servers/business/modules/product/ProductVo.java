package com.betel.servers.business.modules.product;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: ProductVo
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/12 0:18
 */
public class ProductVo extends BaseVo implements IDataBaseVo
{
    private final static String ID = "id";
    private final static String ROLE_LIST = "roleList";

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
