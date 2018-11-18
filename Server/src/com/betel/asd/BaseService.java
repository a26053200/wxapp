package com.betel.asd;

import com.betel.asd.interfaces.IService;
import com.betel.servers.business.modules.brand.BrandDao;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: BaseService
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 0:23
 */
public abstract class BaseService<T> implements IService<T>
{
    public abstract BaseDao<T> getBaseDao();//此种实现可提高扩展性
    public abstract void setBaseDao(BaseDao baseDao);
    @Override
    public void addEntry(T t)
    {
        this.getBaseDao().addEntry(t);
    }

    @Override
    public T getEntryById(String id)
    {
        return this.getBaseDao().getEntryById(id);
    }

    @Override
    public Set<T> getEntrysByIds(String[] ids)
    {
        return this.getBaseDao().getEntrysByIds(ids);
    }

    @Override
    public List<T> getEntrys()
    {
        return this.getBaseDao().getEntrys();
    }

    @Override
    public void updateEntry(T t)
    {
        this.getBaseDao().updateEntry(t);
    }

    @Override
    public void deleteEntriesByIDS(String[] ids)
    {
        this.getBaseDao().deleteEntriesByIDS(ids);
    }

    @Override
    public void deleteEntry(String id)
    {
        this.getBaseDao().deleteEntry(id);
    }
}
