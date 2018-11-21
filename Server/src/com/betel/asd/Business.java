package com.betel.asd;

import com.betel.asd.interfaces.IBusiness;
import com.betel.common.Monitor;
import com.betel.servers.business.action.ImplAction;
import com.betel.servers.business.modules.beans.Spec;
import com.betel.session.Session;

/**
 * @ClassName: Business
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/22 0:30
 */
public abstract class Business<T> implements IBusiness<T>
{
    protected ImplAction action;

    public void setAction(ImplAction action)
    {
        this.action = action;
        monitor = action.getMonitor();
        service = action.getService();
    }

    protected Monitor monitor;
    protected BaseService<T> service;
    @Override
    public T newEntry(Session session)
    {
        return null;
    }

    @Override
    public void updateEntry(Session session, T t)
    {

    }

    @Override
    public void Handle(Session session, String method)
    {

    }
}
