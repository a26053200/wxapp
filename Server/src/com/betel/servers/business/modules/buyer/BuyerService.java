package com.betel.servers.business.modules.buyer;

import com.betel.asd.BaseDao;
import com.betel.asd.BaseService;
import com.betel.servers.business.modules.beans.Buyer;

/**
 * @ClassName: BuyerService
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:54
 */
public class BuyerService extends BaseService<Buyer>
{

    private BuyerDao buyerDao;
    @Override
    public BaseDao<Buyer> getBaseDao()
    {
        return buyerDao;
    }

    @Override
    public void setBaseDao(BaseDao baseDao)
    {
        this.buyerDao = (BuyerDao)baseDao;
    }
}
