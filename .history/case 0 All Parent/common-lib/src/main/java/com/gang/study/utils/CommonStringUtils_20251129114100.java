package com.gang.study.utils;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * <p>
 * 提供常用的字符串处理功能，包括：
 * <ul>
 *     <li>模板字符串替换</li>
 *     <li>驼峰命名与下划线命名互转</li>
 *     <li>JSON 对象属性替换</li>
 * </ul>
 * </p>
 *
 * @author zengzg
 * @since 2019/12/28
 */
public final class CommonStringUtils {

    /** 下划线转驼峰的正则表达式 */
    private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");
    
    /** 驼峰转下划线的正则表达式 */
    private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

    private CommonStringUtils() {
        // 工具类禁止实例化
    }

    /**
     * 使用 JSON 字符串替换模板中的占位符
     *
     * @param templateBody 模板字符串，如 "Hello, ${name}!"
     * @param jsonObject   JSON 格式的参数字符串，如 {"name": "World"}
     * @return 替换后的字符串
     */
    public static String replaceTemplateString(String templateBody, String jsonObject) {
        return replaceTemplate(templateBody, JSONObject.parseObject(jsonObject));
    }

    /**
     * 使用 JSONObject 替换模板中的占位符
     *
     * @param templateBody 模板字符串
     * @param jsonObject   参数对象
     * @return 替换后的字符串
     */
    public static String replaceTemplate(String templateBody, JSONObject jsonObject) {
        return JexlUtils.evaluate(templateBody, jsonObject.getInnerMap());
    }

    /**
     * 截取字符串中指定字符最后一次出现位置之后的内容
     *
     * @param str   原字符串
     * @param vchar 分隔字符
     * @return 最后一个分隔字符之后的子字符串
     */
    public static String lastVarchar(String str, char vchar) {
        return str.substring(str.lastIndexOf(vchar) + 1).trim();
    }

    /**
     * 使用 Map 替换模板中的占位符
     *
     * @param templateBody 模板字符串
     * @param map          参数 Map
     * @return 替换后的字符串
     */
    public String replaceTemplate(String templateBody, Map<String, Object> map) {
        return JexlUtils.evaluate(templateBody, map);
    }

    /**
     * 使用 JSON 字符串中的属性值替换 URL 中的占位符
     *
     * @param url        URL 模板
     * @param jsonString JSON 格式的参数字符串
     * @return 替换后的 URL
     */
    public static String replaceJSONObjectString(String url, String jsonString) {
        return replaceJSONObject(url, JSONObject.parseObject(jsonString));
    }

    /**
     * 使用 JSONObject 中的所有属性值替换 URL 中对应的键名
     *
     * @param url    URL 模板
     * @param params 参数对象
     * @return 替换后的 URL
     */
    public static String replaceJSONObject(String url, JSONObject params) {
        String newUrl = url;
        for (String key : params.keySet()) {
            newUrl = org.apache.commons.lang3.StringUtils.replace(newUrl, key, params.getString(key));
        }
        return newUrl;
    }

    /**
     * 下划线命名转驼峰命名
     * <p>
     * 例如：user_name -> userName
     * </p>
     *
     * @param str 下划线命名的字符串
     * @return 驼峰命名的字符串
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰命名转下划线命名（简单实现）
     * <p>
     * 例如：userName -> user_name
     * </p>
     * <p>
     * 注意：效率低于 {@link #humpToLine2(String)}
     * </p>
     *
     * @param str 驼峰命名的字符串
     * @return 下划线命名的字符串
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 驼峰命名转下划线命名（高效实现）
     * <p>
     * 例如：userName -> user_name
     * </p>
     *
     * @param str 驼峰命名的字符串
     * @return 下划线命名的字符串
     */
    public static String humpToLine2(String str) {
        Matcher matcher = HUMP_PATTERN.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
