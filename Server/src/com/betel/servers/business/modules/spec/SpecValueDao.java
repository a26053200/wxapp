package com.betel.servers.business.modules.spec;

import com.betel.asd.BaseDao;
import com.betel.servers.business.modules.beans.SpecValue;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: SpecValueDao
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/19 23:50
 */
public class SpecValueDao extends BaseDao<SpecValue>
{

    public SpecValueDao(Jedis db)
    {
        super(db, SpecValue.class);
    }
}
