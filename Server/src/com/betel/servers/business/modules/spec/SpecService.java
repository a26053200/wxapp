package com.betel.servers.business.modules.spec;

import com.betel.asd.BaseDao;
import com.betel.asd.BaseService;
import com.betel.servers.business.modules.beans.Brand;
import com.betel.servers.business.modules.beans.Spec;

/**
 * @ClassName: BuyerService
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:54
 */
public class SpecService extends BaseService<Spec>
{

    private SpecDao specDao;
    @Override
    public BaseDao<Spec> getBaseDao()
    {
        return specDao;
    }

    @Override
    public void setBaseDao(BaseDao baseDao)
    {
        this.specDao = (SpecDao)baseDao;
    }
}
