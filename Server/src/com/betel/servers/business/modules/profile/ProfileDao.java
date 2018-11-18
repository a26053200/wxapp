package com.betel.servers.business.modules.profile;

import com.betel.asd.BaseDao;
import com.betel.servers.business.modules.beans.Brand;
import com.betel.servers.business.modules.beans.Profile;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: BuyerDao
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:57
 */
public class ProfileDao extends BaseDao<Profile>
{

    public ProfileDao(Jedis db)
    {
        super(db, Profile.class);
    }
}
