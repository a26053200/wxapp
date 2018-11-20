package com.betel.servers.business.modules.category;

import com.betel.asd.BaseDao;
import com.betel.asd.BaseService;
import com.betel.servers.business.modules.beans.Brand;
import com.betel.servers.business.modules.beans.Category;

/**
 * @ClassName: BuyerService
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:54
 */
public class CategoryService extends BaseService<Category>
{

    private CategoryDao categoryDao;
    @Override
    public BaseDao<Category> getBaseDao()
    {
        return categoryDao;
    }

    @Override
    public void setBaseDao(BaseDao baseDao)
    {
        this.categoryDao = (CategoryDao)baseDao;
    }
}
