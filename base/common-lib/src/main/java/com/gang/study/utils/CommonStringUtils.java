package com.gang.study.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname StringUtils
 * @Description TODO
 * @Date 2019/12/28 14:10
 * @Created by zengzg
 */
public final class CommonStringUtils {

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    private CommonStringUtils() {
    }

    /**
     * @param templateBody
     * @param jsonObject
     * @return
     */
    public static String replaceTemplateString(String templateBody, String jsonObject) {
        return replaceTemplate(templateBody, JSONObject.parseObject(jsonObject));
    }

    /**
     * @param templateBody
     * @param jsonObject
     * @return
     */
    public static String replaceTemplate(String templateBody, JSONObject jsonObject) {
        return JexlUtils.evaluate(templateBody, jsonObject.getInnerMap());
    }


    /**
     * 截取最后一个字符串
     *
     * @param str
     * @param vchar
     * @return
     */
    public static String lastVarchar(String str, char vchar) {
        return str.substring(str.lastIndexOf(vchar) + 1).trim();
    }


    /**
     * desc : 替换为template
     *
     * @param templateBody
     * @param map
     * @return
     */
    public String replaceTemplate(String templateBody, Map<String, Object> map) {
        return JexlUtils.evaluate(templateBody, map);
    }

    /**
     * replace json Type string entity new String
     *
     * @param url
     * @param jsonString
     * @return
     */
    public static String replaceJSONObjectString(String url, String jsonString) {
        return replaceJSONObject(url, JSONObject.parseObject(jsonString));
    }

    /**
     * desc : 替换 JSONObject 中所有的属性
     *
     * @param url
     * @param params
     * @return
     */
    public static String replaceJSONObject(String url, JSONObject params) {

        String newUrl = url;
        for (String key : params.keySet()) {
            newUrl = org.apache.commons.lang3.StringUtils.replace(newUrl, key, params.getString(key));
        }
        return newUrl;
    }

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }


    /**
     * 驼峰转下划线,效率比上面高
     */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
