package com.betel.servers.business.modules.brand;

import com.betel.asd.BaseDao;
import com.betel.servers.business.modules.beans.Brand;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: BuyerDao
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:57
 */
public class BrandDao extends BaseDao<Brand>
{

    public BrandDao(Jedis db)
    {
        super(db, Brand.class);
    }
}
