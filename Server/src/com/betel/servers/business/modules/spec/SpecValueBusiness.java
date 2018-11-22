package com.betel.servers.business.modules.spec;

import com.betel.asd.Business;
import com.betel.consts.FieldName;
import com.betel.servers.business.modules.beans.Spec;
import com.betel.servers.business.modules.beans.SpecValue;
import com.betel.session.Session;
import com.betel.utils.IdGenerator;
import com.betel.utils.TimeUtils;

import java.util.Date;

/**
 * @ClassName: SpecBusiness
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/22 0:46
 */
public class SpecValueBusiness extends Business<SpecValue>
{
    private static final String ViceKey = "specId";
    @Override
    public String getViceKey()
    {
        return ViceKey;
    }
    @Override
    public SpecValue newEntry(Session session)
    {
        String nowTime = TimeUtils.date2String(new Date());

        SpecValue specValueInfo = new SpecValue();
        specValueInfo.setId(Long.toString(IdGenerator.getInstance().nextId()));
        specValueInfo.setSpecId(session.getRecvJson().getString(ViceKey));
        specValueInfo.setValue(session.getRecvJson().getString(FieldName.VALUE));
        specValueInfo.setAddTime(nowTime);
        specValueInfo.setUpdateTime(nowTime);
        return specValueInfo;
    }

    @Override
    public void updateEntry(Session session, SpecValue specValueInfo)
    {
        String nowTime = TimeUtils.date2String(new Date());
        specValueInfo.setValue(session.getRecvJson().getString(FieldName.VALUE));
        specValueInfo.setUpdateTime(nowTime);
    }
}
