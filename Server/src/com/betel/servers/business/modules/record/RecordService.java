package com.betel.servers.business.modules.record;

import com.betel.asd.BaseDao;
import com.betel.asd.BaseService;
import com.betel.servers.business.modules.beans.Brand;
import com.betel.servers.business.modules.beans.Record;

/**
 * @ClassName: BuyerService
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 22:54
 */
public class RecordService extends BaseService<Record>
{

    private RecordDao recordDao;
    @Override
    public BaseDao<Record> getBaseDao()
    {
        return recordDao;
    }

    @Override
    public void setBaseDao(BaseDao baseDao)
    {
        this.recordDao = (RecordDao)baseDao;
    }
}
