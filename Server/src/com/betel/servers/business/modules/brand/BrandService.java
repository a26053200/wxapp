package com.betel.servers.business.modules.brand;

import com.betel.asd.BaseDao;
import com.betel.asd.BaseService;
import com.betel.servers.business.modules.beans.Brand;

/**
 * @ClassName: BuyerService
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:54
 */
public class BrandService extends BaseService<Brand>
{

    private  BrandDao brandDao;
    @Override
    public BaseDao<Brand> getBaseDao()
    {
        return brandDao;
    }

    @Override
    public void setBaseDao(BaseDao baseDao)
    {
        this.brandDao = (BrandDao)baseDao;
    }
}
