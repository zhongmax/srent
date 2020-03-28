package com.csmaxwell.srent.core.system;

import com.csmaxwell.srent.core.util.SystemInfoPrinter;
import com.csmaxwell.srent.db.service.SrentSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统启动服务，用于设置系统配置信息、检查系统状态及打印系统信息
 */
@Component
class SystemInistService {
    private SystemInistService systemInistService;


    @Autowired
    private Environment environment;

    @PostConstruct
    private void inist() {
        systemInistService = this;
        initConfigs();
        SystemInfoPrinter.printInfo("Srent 初始化信息", getSystemInfo());
    }


    private final static Map<String, String> DEFAULT_CONFIGS = new HashMap<>();
    static {
        // 小程序相关配置默认值
        DEFAULT_CONFIGS.put(SystemConfig.Srent_WX_INDEX_NEW, "6");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_WX_INDEX_HOT, "6");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_WX_INDEX_BRAND, "4");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_WX_INDEX_TOPIC, "4");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_WX_INDEX_CATLOG_LIST, "4");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_WX_INDEX_CATLOG_GOODS, "4");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_WX_SHARE, "false");
        // 运费相关配置默认值
        DEFAULT_CONFIGS.put(SystemConfig.Srent_EXPRESS_FREIGHT_VALUE, "8");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_EXPRESS_FREIGHT_MIN, "88");
        // 订单相关配置默认值
        DEFAULT_CONFIGS.put(SystemConfig.Srent_ORDER_UNPAID, "30");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_ORDER_UNCONFIRM, "7");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_ORDER_COMMENT, "7");
        // 订单相关配置默认值
        DEFAULT_CONFIGS.put(SystemConfig.Srent_MALL_NAME, "srent");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_MALL_ADDRESS, "上海");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_MALL_PHONE, "021-xxxx-xxxx");
        DEFAULT_CONFIGS.put(SystemConfig.Srent_MALL_QQ, "738696120");
    }

    @Autowired
    private SrentSystemConfigService srentSystemConfigService;

    private void initConfigs() {
        // 1. 读取数据库全部配置信息
        Map<String, String> configs = srentSystemConfigService.queryAll();

        // 2. 分析DEFAULT_CONFIGS
        for (Map.Entry<String, String> entry : DEFAULT_CONFIGS.entrySet()) {
            if(configs.containsKey(entry.getKey())){
                continue;
            }

            configs.put(entry.getKey(), entry.getValue());
            srentSystemConfigService.addConfig(entry.getKey(), entry.getValue());
        }

        SystemConfig.setConfigs(configs);
    }

    private Map<String, String> getSystemInfo() {

        Map<String, String> infos = new LinkedHashMap<>();

        infos.put(SystemInfoPrinter.CREATE_PART_COPPER + 0, "系统信息");
        // 测试获取application-db.yml配置信息
        infos.put("服务器端口", environment.getProperty("server.port"));
        infos.put("数据库USER", environment.getProperty("spring.datasource.druid.username"));
        infos.put("数据库地址", environment.getProperty("spring.datasource.druid.url"));

        infos.put("对象存储", environment.getProperty("srent.storage.active"));
//        infos.put("本地对象存储路径", environment.getProperty("srent.storage.local" +
//                ".storagePath"));
//        infos.put("本地对象访问地址", environment.getProperty("srent.storage.local" +
//                ".address"));
        infos.put("本地对象访问端口", environment.getProperty("srent.storage.local" +
                ".port"));

        // 微信相关信息
        infos.put(SystemInfoPrinter.CREATE_PART_COPPER + 2, "微信相关");
        infos.put("微信APP KEY", environment.getProperty("srent.wx.app-id"));
        infos.put("微信APP-SECRET", environment.getProperty("srent.wx" +
                ".app-secret"));


        return infos;
    }
}

