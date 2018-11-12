package com.betel.servers.business.modules.record;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.Monitor;
import com.betel.common.SubMonitor;
import com.betel.servers.business.modules.buyer.BuyerVo;
import com.betel.utils.IdGenerator;
import com.betel.utils.TimeUtils;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @ClassName: RecordMnt
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/20 22:07
 */
public class RecordMnt extends SubMonitor
{
    public RecordMnt(Monitor base)
    {
        super(base);
    }

    @Override
    public void ActionHandler(ChannelHandlerContext ctx, JSONObject jsonObject, String subAction)
    {

    }

    /**
     * 记录买家登录
     * @param buyer 买家
     * @return
     */
    public RecordVo addBuyerLoginRecord(BuyerVo buyer)
    {
        // 添加一次登陆记录
        long recordId = IdGenerator.getInstance().nextId();//生成玩家Id
        RecordVo loginRecord = new RecordVo(buyer.getId(), Long.toString(recordId),"Login");
        loginRecord.setContent("微信买家登录");
        loginRecord.setAddTime(TimeUtils.date2String(new Date()));
        loginRecord.writeDB(db);
        return loginRecord;
    }
}
