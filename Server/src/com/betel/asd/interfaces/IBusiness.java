package com.betel.asd.interfaces;

import com.betel.session.Session;

public interface IBusiness<T>
{
    public T newEntry(Session session);

    public void updateEntry(Session session, T t);

    void Handle(Session session, String method);
}
