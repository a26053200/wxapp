package com.betel.servers.business.modules.seller;

import com.betel.asd.BaseDao;
import com.betel.servers.business.modules.beans.Seller;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: BuyerDao
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:57
 */
public class SellerDao extends BaseDao<Seller>
{

    public SellerDao(Jedis db)
    {
        super(db, Seller.class);
    }
}
