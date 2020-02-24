package com.gang.study.utils;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @Classname TemplateUtil
 * @Description TODO
 * @Date 2019/10/31 22:46
 * @Created by ant-black 1016930479@qq.com
 */
public class TemplateResolve {

    public static Map<String, String> getTemplateParamMap(String templateBody) {
        HashMap backMap = new HashMap<String, String>();
        return backMap;
    }

    /**
     * 默认前缀占位符
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * 默认后缀占位符
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    /**
     * 默认单例解析器
     */
    private static TemplateResolve defaultResolver = new TemplateResolve();

    private TemplateResolve() {
    }

    /**
     * 获取默认的占位符解析器，即占位符前缀为"${", 后缀为"}"
     *
     * @return
     */
    public TemplateResolve getDefaultResolver() {
        return defaultResolver;
    }

    public static String jexlResolve(String template, String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return JexlUtils.evaluate(template, map);
    }

    public static String jexlResolve(String template, String content) {
        Map<String, Object> map = JSONObject.parseObject(content);
        return JexlUtils.evaluate(template, map);
    }

    public static String resolve(String template, String key, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        return resolve(template, jsonObject.toJSONString());
    }

    /**
     * 解析带有指定占位符的模板字符串，默认占位符为前缀：${  后缀：}<br/><br/>
     * 如：templates = category:${}:product:${}<br/>
     * values = {"1", "2"}<br/>
     * 返回 category:1:product:2<br/>
     *
     * @param content 要解析的带有占位符的模板字符串
     * @param values  按照模板占位符索引位置设置对应的值
     * @return
     */
    public static String resolve(String content, String... values) {
        int start = content.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        if (start == -1) {
            return content;
        }
        //值索引
        int valueIndex = 0;
        StringBuilder result = new StringBuilder(content);
        while (start != -1) {
            int end = result.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
            String replaceContent = values[valueIndex++];
            result.replace(start, end + DEFAULT_PLACEHOLDER_SUFFIX.length(), replaceContent);
            start = result.indexOf(DEFAULT_PLACEHOLDER_PREFIX, start + replaceContent.length());
        }
        return result.toString();
    }

    /**
     * 解析带有指定占位符的模板字符串，默认占位符为前缀：${  后缀：}<br/><br/>
     * 如：templates = category:${}:product:${}<br/>
     * values = {"1", "2"}<br/>
     * 返回 category:1:product:2<br/>
     *
     * @param content 要解析的带有占位符的模板字符串
     * @param values  按照模板占位符索引位置设置对应的值
     * @return
     */
    public static String resolve(String content, Object[] values) {
        return resolve(content, Stream.of(values).map(String::valueOf).toArray(String[]::new));
    }

    /**
     * 根据替换规则来替换指定模板中的占位符值
     *
     * @param content 要解析的字符串
     * @param rule    解析规则回调
     * @return
     */
    public static String resolveByRule(String content, Function<String, String> rule) {
        int start = content.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        if (start == -1) {
            return content;
        }
        StringBuilder result = new StringBuilder(content);
        while (start != -1) {
            int end = result.indexOf(DEFAULT_PLACEHOLDER_SUFFIX, start);
            //获取占位符属性值，如${id}, 即获取id
            String placeholder = result.substring(start + DEFAULT_PLACEHOLDER_PREFIX.length(), end);
            //替换整个占位符内容，即将${id}值替换为替换规则回调中的内容
            String replaceContent = placeholder.trim().isEmpty() ? "" : rule.apply(placeholder);
            result.replace(start, end + DEFAULT_PLACEHOLDER_SUFFIX.length(), replaceContent);
            start = result.indexOf(DEFAULT_PLACEHOLDER_PREFIX, start + replaceContent.length());
        }
        return result.toString();
    }

    /**
     * 替换模板中占位符内容，占位符的内容即为map key对应的值，key为占位符中的内容。<br/><br/>
     * 如：content = product:${id}:detail:${did}<br/>
     * valueMap = id -> 1; pid -> 2<br/>
     * 经过解析返回 product:1:detail:2<br/>
     *
     * @param content  模板内容。
     * @param valueMap 值映射
     * @return 替换完成后的字符串。
     */
    public static String resolveByMap(String content, final Map<String, Object> valueMap) {
        return resolveByRule(content, placeholderValue -> String.valueOf(valueMap.get(placeholderValue)));
    }

    /**
     * 根据properties文件替换占位符内容
     *
     * @param content
     * @param properties
     * @return
     */
    public static String resolveByProperties(String content, final Properties properties) {
        return resolveByRule(content, placeholderValue -> properties.getProperty(placeholderValue));
    }

    /**
     * 根据对象中字段路径(即类似js访问对象属性值)替换模板中的占位符 <br/><br/>
     * 如 content = product:${id}:detail:${detail.id} <br/>
     * obj = Product.builder().id(1).detail(Detail.builder().id(2).build()).build(); <br/>
     * 经过解析返回 product:1:detail:2 <br/>
     *
     * @param content 要解析的内容
     * @param obj     填充解析内容的对象(如果是基本类型，则所有占位符替换为相同的值)
     * @return
     */
    public static String resolveByObject(String content, final Object obj) {
        if (obj instanceof Map) {
            return resolveByMap(content, (Map) obj);
        }
        return resolveByRule(content, placeholderValue -> String.valueOf(ReflectionUtils.getValueByFieldPath(obj,
                placeholderValue)));
    }

}
