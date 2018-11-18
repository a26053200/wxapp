package com.betel.servers.business.modules.buyer;

import com.betel.asd.BaseDao;
import com.betel.servers.business.modules.beans.Brand;
import com.betel.servers.business.modules.beans.Buyer;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: BuyerDao
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:57
 */
public class BuyerDao extends BaseDao<Buyer>
{

    public BuyerDao(Jedis db)
    {
        super(db, Buyer.class);
    }
}
