package com.betel.consts;

/**
 * @ClassName: Action
 * @Description: Action
 * @Author: zhengnan
 * @Date: 2018/6/6 20:03
 */
public class Action
{
    /**
     *
     */
    public final static String NAME = "action";

    /**
     *
     */
    public final static String NONE = "none";


    /**
     * 网关服务器和均衡服务器握手
     */
    public final static String HANDSHAKE_GATE2BALANCE = "handshake_gate2balance";

    /**
     * 业务服务器和均衡服务器握手
     */
    public final static String HANDSHAKE_BUSINESS2BALANCE = "handshake_business2balance";

    /**
     * 小程序探测
     */
    public final static String MP_PROBE = "mp_probe";

    /**
     * 小程序通过服务器登陆微信官方接口服务器
     */
    public final static String MP_LOGIN_WX_SERVER = "mp_login_wx_server";

    /**
     * 小程序用户获取个人信息
     */
    public final static String MP_GET_PROFILE = "mp_get_profile";
}
