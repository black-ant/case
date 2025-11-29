package com.gang.study.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

/**
 * JSON 处理工具类
 * <p>
 * 基于 Fastjson2 封装的 JSON 解析工具。
 * </p>
 *
 * <h3>使用示例：</h3>
 * <pre>{@code
 * String json = "{\"name\":\"张三\",\"age\":18}";
 * String name = JSONUtils.getJSONParam("name", json); // 张三
 * }</pre>
 *
 * @author ant-black
 * @since 2019/10/31
 */
public final class JSONUtils {

    private JSONUtils() {
        // 工具类禁止实例化
    }

    /**
     * 从 JSON 字符串中获取指定键的值
     *
     * @param key  JSON 键名
     * @param json JSON 字符串
     * @return 对应键的值的字符串表示，如果键不存在则返回 "null"
     */
    public static String getJSONParam(String key, String json) {
        return String.valueOf(JSONObject.parseObject(json).get(key));
    }

    /**
     * 将对象转换为 JSON 字符串
     *
     * @param obj 要转换的对象
     * @return JSON 字符串
     */
    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 将 JSON 字符串解析为指定类型的对象
     *
     * @param json  JSON 字符串
     * @param clazz 目标类型
     * @param <T>   泛型类型
     * @return 解析后的对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将 JSON 字符串解析为 JSONObject
     *
     * @param json JSON 字符串
     * @return JSONObject 对象
     */
    public static JSONObject parseObject(String json) {
        return JSONObject.parseObject(json);
    }
}
