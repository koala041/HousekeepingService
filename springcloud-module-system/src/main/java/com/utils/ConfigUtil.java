package com.utils;

import com.alibaba.fastjson.JSONObject;
import com.entity.ConfigEntity;
import com.service.impl.ConfigServiceImpl;

import java.util.Map;

/**
 * 配置工具类，用于外部访问缓存的配置
 */
public class ConfigUtil {

    /**
     * 获取所有配置
     *
     * @return 配置映射
     */
    public static Map<String, ConfigEntity> getAllConfigs() {
        return ConfigServiceImpl.getConfigCache();
    }

    /**
     * 根据配置名获取配置值
     *
     * @param name 配置名
     * @return 配置值
     */
    public static String getConfigValue(String name) {
        ConfigEntity config = ConfigServiceImpl.getConfigCache().get(name);
        return config != null ? config.getValue() : null;
    }

    /**
     * 根据配置名获取配置值
     *
     * @param name 配置名
     * @param jsonKey 配置JSON对象中的key
     * @return 配置值
     */
    public static String getConfigValue(String name, String jsonKey) {
        ConfigEntity config = ConfigServiceImpl.getConfigCache().get(name);
        if (config != null && config.getValue() != null) {
            return JSONObject.parseObject(config.getValue()).getString(jsonKey);
        }
        return config != null ? config.getValue() : null;
    }

    /**
     * 根据配置名获取配置URL
     *
     * @param name 配置名
     * @return 配置URL
     */
    public static String getConfigUrl(String name) {
        ConfigEntity config = ConfigServiceImpl.getConfigCache().get(name);
        Object url = CommonUtil.getPropertyByFieldNames(config, "url");
        return config != null ? (null != null ? url.toString() : null) : null;
    }

    /**
     * 根据配置名获取配置类型
     *
     * @param name 配置名
     * @return 配置类型
     */
    public static String getConfigType(String name) {
        ConfigEntity config = ConfigServiceImpl.getConfigCache().get(name);
        return config != null ? config.getType() : null;
    }
}
