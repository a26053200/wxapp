package com.betel.servers.business.modules.spec;

import com.betel.asd.BaseDao;
import com.betel.asd.BaseService;
import com.betel.servers.business.modules.beans.SpecValue;

/**
 * @ClassName: SpecValueService
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/19 23:51
 */
public class SpecValueService extends BaseService<SpecValue>
{

    private SpecValueDao specValueDao;
    @Override
    public BaseDao<SpecValue> getBaseDao()
    {
        return specValueDao;
    }

    @Override
    public void setBaseDao(BaseDao baseDao)
    {
        this.specValueDao = (SpecValueDao)baseDao;
    }
}
