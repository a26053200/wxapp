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
     * 行为字段名
     */
    public final static String NAME = "action";

    /**
     * 空的行为
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
     * 小程序探测服务器是否开启
     */
    public final static String MP_PROBE = "mp_probe";

    /**
     * 小程序通过服务器登录微信官方接口服务器
     */
    public final static String MP_LOGIN_WX_SERVER = "mp_login_wx_server";

    /**
     * 小程序用户获取个人信息
     */
    public final static String MP_GET_PROFILE = "mp_get_profile";

    /**
     * 小程序用扫码web端登录
     */
    public final static String MP_SCAN_WEB_LOGIN = "mp_scan_web_login";

    /**
     * Web管理页面探测服务器是否开启
     */
    public final static String WEB_ADMIN_PROBE = "mp_probe";

    /**
     * web端请求登录的二维码
     */
    public final static String WEB_RQST_SCAN_CODE_LOGIN = "web_rqst_login_QR_code";

    /**
     * web端扫码登录
     */
    public final static String WEB_LOGIN = "web_login";

    /**
     * 商家登录服务器
     */
    public final static String WEB_SELLER_LOGIN_SERVER = "web_seller_login_server";
}
