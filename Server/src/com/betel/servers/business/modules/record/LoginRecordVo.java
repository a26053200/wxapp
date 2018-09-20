package com.betel.servers.business.modules.record;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;
import com.betel.common.interfaces.IDataBaseVo;
import com.betel.database.RedisKeys;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: 登陆记录
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/20 22:00
 */
public class LoginRecordVo extends BaseVo implements IDataBaseVo
{
    private String id;
    private String buyerId;
    private String loginTime;   //买家登陆时间
    private String logoutTime;  //买家登出时间

    public LoginRecordVo(String buyerId, String id)
    {
        super(RedisKeys.record + ":" + buyerId + ":" + id);
        this.buyerId = buyerId;
        this.id = id;
    }
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getBuyerId()
    {
        return buyerId;
    }

    public String getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(String loginTime)
    {
        this.loginTime = loginTime;
    }

    public String getLogoutTime()
    {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime)
    {
        this.logoutTime = logoutTime;
    }

    @Override
    public JSONObject toJson()
    {
        return null;
    }

    @Override
    public void fromDB(Jedis db)
    {

    }

    @Override
    public void writeDB(Jedis db)
    {
        db.hset(primaryKey, RedisKeys.record_id,            id);
        db.hset(primaryKey, RedisKeys.buyer_id,             buyerId);
        db.hset(primaryKey, RedisKeys.record_login_time,    loginTime);
        if(logoutTime != null)
            db.hset(primaryKey, RedisKeys.record_logout_time,       logoutTime);
    }
}
