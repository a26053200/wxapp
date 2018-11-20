package com.betel.servers.business.modules.category;

import com.betel.asd.BaseDao;
import com.betel.servers.business.modules.beans.Brand;
import com.betel.servers.business.modules.beans.Category;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: BuyerDao
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:57
 */
public class CategoryDao extends BaseDao<Category>
{

    public CategoryDao(Jedis db)
    {
        super(db, Category.class);
    }
}
