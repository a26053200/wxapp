package com.betel.servers.business.modules.brand;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.betel.asd.BaseAction;
import com.betel.common.Monitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.servers.business.modules.beans.Brand;
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
public class BrandAction extends BaseAction<Brand>
{
    public BrandAction(Monitor monitor)
    {
        this.monitor = monitor;
        this.service = new BrandService();
        this.service.setBaseDao(new BrandDao(monitor.getDB()));
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String method)
    {
        Session session = new Session(ctx, jsonObject);
        switch (method)
        {
            case Action.NONE:
                break;
            case Action.ADD_BRAND:
                addBrand(session);
                break;
            case Action.BRAND_LIST:
                getBrandList(session);
                break;
            default:
                break;
        }
    }
    private void addBrand(Session session)
    {
        String nowTime = TimeUtils.date2String(new Date());

        Brand brandInfo = new Brand();
        brandInfo.setId(Long.toString(IdGenerator.getInstance().nextId()));
        brandInfo.setName(session.getRecvJson().getString(FieldName.NAME));
        brandInfo.setAddTime(nowTime);
        brandInfo.setUpdateTime(nowTime);
        service.addEntry(brandInfo);

        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.BRAND_INFO, JsonUtils.object2Json(brandInfo));
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }

    private void getBrandList(Session session)
    {
        JSONObject sendJson = new JSONObject();
        Iterator<Brand> it = service.getEntrys().iterator();
        JSONArray array = new JSONArray();
        int count = 0;
        while (it.hasNext())
        {
            JSONObject item = JsonUtils.object2Json(it.next());
            item.put(FieldName.KEY,Integer.toString(count));
            array.add(count++,item);
        }
        sendJson.put(FieldName.BRAND_LIST,array);
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }
}
