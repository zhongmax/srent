package com.csmaxwell.srent.core.system;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统设置
 */
public class SystemConfig {
    // 小程序相关配置
    public final static String Srent_WX_INDEX_NEW = "srent_wx_index_new";
    public final static String Srent_WX_INDEX_HOT = "srent_wx_index_hot";
    public final static String Srent_WX_INDEX_BRAND = "srent_wx_index_brand";
    public final static String Srent_WX_INDEX_TOPIC = "srent_wx_index_topic";
    public final static String Srent_WX_INDEX_CATLOG_LIST = "srent_wx_catlog_list";
    public final static String Srent_WX_INDEX_CATLOG_GOODS = "srent_wx_catlog_goods";
    public final static String Srent_WX_SHARE = "srent_wx_share";
    public final static String Srent_WX_INDEX_ALL = "srent_wx_index_all";
    // 运费相关配置
    public final static String Srent_EXPRESS_FREIGHT_VALUE = "srent_express_freight_value";
    public final static String Srent_EXPRESS_FREIGHT_MIN = "srent_express_freight_min";
    // 订单相关配置
    public final static String Srent_ORDER_UNPAID = "srent_order_unpaid";
    public final static String Srent_ORDER_UNCONFIRM = "srent_order_unconfirm";
    public final static String Srent_ORDER_COMMENT = "srent_order_comment";
    // 商场相关配置
    public final static String Srent_MALL_NAME = "srent_mall_name";
    public final static String Srent_MALL_ADDRESS = "srent_mall_address";
    public final static String Srent_MALL_PHONE = "srent_mall_phone";
    public final static String Srent_MALL_QQ = "srent_mall_qq";

    //所有的配置均保存在该 HashMap 中
    private static Map<String, String> SYSTEM_CONFIGS = new HashMap<>();

    private static String getConfig(String keyName) {
        return SYSTEM_CONFIGS.get(keyName);
    }

    private static Integer getConfigInt(String keyName) {
        return Integer.parseInt(SYSTEM_CONFIGS.get(keyName));
    }

    private static Boolean getConfigBoolean(String keyName) {
        return Boolean.valueOf(SYSTEM_CONFIGS.get(keyName));
    }

    private static BigDecimal getConfigBigDec(String keyName) {
        return new BigDecimal(SYSTEM_CONFIGS.get(keyName));
    }

    public static Integer getAllLimit() {
        return getConfigInt(Srent_WX_INDEX_ALL);
    }

    public static Integer getNewLimit() {
        return getConfigInt(Srent_WX_INDEX_NEW);
    }

    public static Integer getHotLimit() {
        return getConfigInt(Srent_WX_INDEX_HOT);
    }

    public static Integer getBrandLimit() {
        return getConfigInt(Srent_WX_INDEX_BRAND);
    }

    public static Integer getTopicLimit() {
        return getConfigInt(Srent_WX_INDEX_TOPIC);
    }

    public static Integer getCatlogListLimit() {
        return getConfigInt(Srent_WX_INDEX_CATLOG_LIST);
    }

    public static Integer getCatlogMoreLimit() {
        return getConfigInt(Srent_WX_INDEX_CATLOG_GOODS);
    }

    public static boolean isAutoCreateShareImage() {
        return getConfigBoolean(Srent_WX_SHARE);
    }

    public static BigDecimal getFreight() {
        return getConfigBigDec(Srent_EXPRESS_FREIGHT_VALUE);
    }

    public static BigDecimal getFreightLimit() {
        return getConfigBigDec(Srent_EXPRESS_FREIGHT_MIN);
    }

    public static Integer getOrderUnpaid() {
        return getConfigInt(Srent_ORDER_UNPAID);
    }

    public static Integer getOrderUnconfirm() {
        return getConfigInt(Srent_ORDER_UNCONFIRM);
    }

    public static Integer getOrderComment() {
        return getConfigInt(Srent_ORDER_COMMENT);
    }

    public static String getMallName() {
        return getConfig(Srent_MALL_NAME);
    }

    public static String getMallAddress() {
        return getConfig(Srent_MALL_ADDRESS);
    }

    public static String getMallPhone() {
        return getConfig(Srent_MALL_PHONE);
    }

    public static String getMallQQ() {
        return getConfig(Srent_MALL_QQ);
    }

    public static void setConfigs(Map<String, String> configs) {
        SYSTEM_CONFIGS = configs;
    }

    public static void updateConfigs(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            SYSTEM_CONFIGS.put(entry.getKey(), entry.getValue());
        }
    }
}
