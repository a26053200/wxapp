package com.betel.servers.business.modules.spec;

import com.betel.asd.BaseDao;
import com.betel.servers.business.modules.beans.Spec;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: BuyerDao
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:57
 */
public class SpecDao extends BaseDao<Spec>
{

    public SpecDao(Jedis db)
    {
        super(db, Spec.class);
    }
}
