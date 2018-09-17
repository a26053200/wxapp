package com.betel.servers.business.modules.buyer;

import com.alibaba.fastjson.JSONObject;
import com.betel.common.BaseVo;

/**
 * @ClassName: BuyerVo
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/13 0:21
 */
public class BuyerVo extends BaseVo
{
    private String id;
    private String openid;
    private String unionid;
    private String registerTime;    //买家第一次登陆游戏时间,即买家在该服务器的注册时间
    private String lastLoginTime;   //买家登陆时间

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

    public String getUnionid()
    {
        return unionid;
    }

    public void setUnionid(String unionid)
    {
        this.unionid = unionid;
    }

    public String getRegisterTime()
    {
        return registerTime;
    }

    public void setRegisterTime(String registerTime)
    {
        this.registerTime = registerTime;
    }

    public String getLastLoginTime()
    {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime)
    {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public JSONObject toJson()
    {
        return null;
    }
}
