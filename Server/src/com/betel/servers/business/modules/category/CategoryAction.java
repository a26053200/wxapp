package com.betel.servers.business.modules.category;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.betel.asd.BaseAction;
import com.betel.common.Monitor;
import com.betel.consts.Action;
import com.betel.consts.FieldName;
import com.betel.consts.ServerName;
import com.betel.servers.business.modules.beans.Brand;
import com.betel.servers.business.modules.beans.Category;
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
public class CategoryAction extends BaseAction<Category>
{
    public CategoryAction(Monitor monitor)
    {
        this.monitor = monitor;
        this.service = new CategoryService();
        this.service.setBaseDao(new CategoryDao(monitor.getDB()));
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String method)
    {
        Session session = new Session(ctx, jsonObject);
        switch (method)
        {
            case Action.NONE:
                break;
            case Action.ADD_CATEGORY:
                addCategory(session);
                break;
            case Action.CATEGORY_LIST:
                getCategoryList(session);
                break;
            default:
                break;
        }
    }
    private void addCategory(Session session)
    {
        String nowTime = TimeUtils.date2String(new Date());

        Category categoryInfo = new Category();
        categoryInfo.setId(Long.toString(IdGenerator.getInstance().nextId()));
        categoryInfo.setName(session.getRecvJson().getString(FieldName.NAME));
        categoryInfo.setAddTime(nowTime);
        categoryInfo.setUpdateTime(nowTime);
        service.addEntry(categoryInfo);

        JSONObject sendJson = new JSONObject();
        sendJson.put(FieldName.CATEGORY_INFO, JsonUtils.object2Json(categoryInfo));
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }

    private void getCategoryList(Session session)
    {
        JSONObject sendJson = new JSONObject();
        Iterator<Category> it = service.getEntrys().iterator();
        JSONArray array = new JSONArray();
        int count = 0;
        while (it.hasNext())
        {
            JSONObject item = JsonUtils.object2Json(it.next());
            item.put(FieldName.KEY,Integer.toString(count));
            array.add(count++,item);
        }
        sendJson.put(FieldName.CATEGORY_LIST,array);
        rspdClient(session, sendJson, ServerName.CLIENT_WEB);
    }
}
