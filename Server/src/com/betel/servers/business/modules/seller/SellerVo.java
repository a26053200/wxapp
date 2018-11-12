package com.betel.servers.business.modules.seller;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: 买家信息
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/8 22:53
 */
public class SellerVo extends BaseVo implements IDataBaseVo
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
