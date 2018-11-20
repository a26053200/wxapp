package com.betel.servers.business.modules.spec;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.betel.asd.BaseAction;
import com.betel.common.Monitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.servers.business.modules.beans.Spec;
import com.betel.servers.business.modules.beans.SpecValue;
import com.betel.session.Session;
import com.betel.utils.IdGenerator;
import com.betel.utils.JsonUtils;
import com.betel.utils.TimeUtils;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.Iterator;

/**
 * @ClassName: BuyerAction
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/11/18 23:00
 */
public class SpecAction extends BaseAction<Spec>
{
    private SpecValueService specValueService;

    public SpecAction(Monitor monitor)
    {
        this.monitor = monitor;
        this.service = new SpecService();
        this.service.setBaseDao(new SpecDao(monitor.getDB()));
        this.specValueService = new SpecValueService();
        this.specValueService.setBaseDao(new SpecValueDao(monitor.getDB()));
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String method)
    {
        Session session = new Session(ctx, jsonObject);
        switch (method)
        {
            case Action.ADD_SPEC:
                addSpec(session);
                break;
            case Action.SPEC_LIST:
                getSpecList(session);
                break;
            case Action.ADD_SPEC_VALUE:
                addSpecValue(session);
                break;
            case Action.SPEC_VALUE_LIST:
                getSpecValueList(session);
                break;
            default:
                break;
        }
    }

    private void addSpec(Session session)
    {
        String nowTime = TimeUtils.date2String(new Date());

        Spec specInfo = new Spec();
        specInfo.setId(Long.toString(IdGenerator.getInstance().nextId()));
        specInfo.setNumber(session.getRecvJson().getString(FieldName.NUMBER));
        specInfo.setName(session.getRecvJson().getString(FieldName.NAME));
        specInfo.setAddTime(nowTime);
        specInfo.setUpdateTime(nowTime);
        service.addEntry(specInfo);

        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.SPEC_INFO, JsonUtils.object2Json(specInfo));
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }

    private void getSpecList(Session session)
    {
        JSONObject sendJson = new JSONObject();
        Iterator<Spec> it = service.getEntrys().iterator();
        JSONArray array = new JSONArray();
        int count = 0;
        while (it.hasNext())
        {
            JSONObject item = JsonUtils.object2Json(it.next());
            item.put(FieldName.KEY, Integer.toString(count));
            array.add(count++, item);
        }
        sendJson.put(FieldName.SPEC_LIST, array);
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }

    private void addSpecValue(Session session)
    {
        String nowTime = TimeUtils.date2String(new Date());

        SpecValue specValueInfo = new SpecValue();
        specValueInfo.setId(Long.toString(IdGenerator.getInstance().nextId()));
        specValueInfo.setSpecId(session.getRecvJson().getString(FieldName.ID));
        specValueInfo.setValue(session.getRecvJson().getString(FieldName.VALUE));
        specValueInfo.setAddTime(nowTime);
        specValueInfo.setUpdateTime(nowTime);
        specValueService.addEntry(specValueInfo);

        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.SPEC_VALUE_INFO, JsonUtils.object2Json(specValueInfo));
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }

    private void getSpecValueList(Session session)
    {
        JSONObject sendJson = new JSONObject();
        String specId = session.getRecvJson().getString(FieldName.ID);
        Iterator<SpecValue> it = specValueService.getEntrys().iterator();
        JSONArray array = new JSONArray();
        int count = 0;
        while (it.hasNext())
        {
            SpecValue specValueInfo = it.next();

            JSONObject item = JsonUtils.object2Json(it.next());
            if (specId.equals(specValueInfo.getSpecId()))
            {
                item.put(FieldName.KEY, Integer.toString(count));
                array.add(count++, item);
            }
        }
        sendJson.put(FieldName.SPEC_VALUE_LIST, array);
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }
}
