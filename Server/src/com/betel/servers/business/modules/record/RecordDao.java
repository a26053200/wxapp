package com.betel.servers.business.modules.record;

import com.betel.asd.BaseDao;
import com.betel.servers.business.modules.beans.Record;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: BuyerDao
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:57
 */
public class RecordDao extends BaseDao<Record>
{

    public RecordDao(Jedis db)
    {
        super(db, Record.class);
    }
}
