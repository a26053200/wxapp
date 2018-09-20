package com.betel.database;

/**
 * @ClassName: RedisKeys
 * @Description: TODO
 * @Author: zhengnan
 * @Date: 2018/9/18 23:07
 */
public class RedisKeys
{
    /**
     * Common
     */

    /**
     * 买家
     */
    public static final String buyer = "buyer";
    public static final String buyer_id = "buyer_id";
    public static final String buyer_open_id = "buyer_open_id";
    public static final String buyer_union_id = "buyer_union_id";
    public static final String buyer_register_time = "buyer_register_time";
    // 买家微信信息
    public static final String buyer_wx_nickname = "buyer_wx_nickname";
    public static final String buyer_wx_gender = "buyer_wx_gender";
    public static final String buyer_wx_language = "buyer_wx_language";
    public static final String buyer_wx_city = "buyer_wx_city";
    public static final String buyer_wx_province = "buyer_wx_province";
    public static final String buyer_wx_country = "buyer_wx_country";
    public static final String buyer_wx_avatar_url = "buyer_wx_avatar_url";

    /**
     * 记录
     */
    public static final String record = "record";
    public static final String record_id = "record_id";
    public static final String record_login_time = "record_login_time";
    public static final String record_logout_time = "record_logout_time";
}
