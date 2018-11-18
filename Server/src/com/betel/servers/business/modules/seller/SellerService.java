package com.betel.servers.business.modules.seller;

import com.betel.asd.BaseDao;
import com.betel.asd.BaseService;
import com.betel.servers.business.modules.beans.Seller;

/**
 * @ClassName: BuyerService
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:54
 */
public class SellerService extends BaseService<Seller>
{

    private SellerDao sellerDao;
    @Override
    public BaseDao<Seller> getBaseDao()
    {
        return sellerDao;
    }

    @Override
    public void setBaseDao(BaseDao baseDao)
    {
        this.sellerDao = (SellerDao)baseDao;
    }
}
