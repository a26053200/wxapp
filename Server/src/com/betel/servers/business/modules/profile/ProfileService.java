package com.betel.servers.business.modules.profile;

import com.betel.asd.BaseDao;
import com.betel.asd.BaseService;
import com.betel.servers.business.modules.beans.Profile;

/**
 * @ClassName: BuyerService
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:54
 */
public class ProfileService extends BaseService<Profile>
{

    private ProfileDao profileDao;
    @Override
    public BaseDao<Profile> getBaseDao()
    {
        return profileDao;
    }

    @Override
    public void setBaseDao(BaseDao baseDao)
    {
        this.profileDao = (ProfileDao)baseDao;
    }
}
