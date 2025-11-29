package com.gang.study.utils;

import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 模板解析工具类
 * <p>
 * 提供字符串模板的占位符替换功能，支持多种替换方式：
 * <ul>
 *     <li>按顺序替换：使用 ${} 占位符按顺序替换</li>
 *     <li>按名称替换：使用 ${name} 占位符按名称替换</li>
 *     <li>自定义规则替换：使用回调函数自定义替换逻辑</li>
 * </ul>
 * </p>
 *
 * <h3>使用示例：</h3>
 * <pre>{@code
 * // 按顺序替换
 * String result = TemplateResolve.resolve("Hello, ${}!", "World");
 * // 输出：Hello, World!
 *
 * // 按名称替换
 * Map<String, Object> params = new HashMap<>();
 * params.put("name", "张三");
 * String result = TemplateResolve.resolveByMap("Hello, ${name}!", params);
 * // 输出：Hello, 张三!
 *
 * // 使用 JEXL 表达式
 * String result = TemplateResolve.jexlResolve("Hello, ${name}!", "name", "World");
 * // 输出：Hello, World!
 * }</pre>
 *
 * @author ant-black
 * @since 2019/10/31
 */
public class TemplateResolve {

    /** 默认前缀占位符 */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /** 默认后缀占位符 */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    /** 默认单例解析器 */
    private static final TemplateResolve DEFAULT_RESOLVER = new TemplateResolve();

    private TemplateResolve() {
        // 私有构造函数
    }

    /**
     * 获取模板参数映射（预留接口）
     *
     * @param templateBody 模板内容
     * @return 参数映射
     */
    public static Map<String, String> getTemplateParamMap(String templateBody) {
        return new HashMap<>();
    }

    /**
     * 获取默认的占位符解析器
     *
     * @return 默认解析器实例
     */
    public TemplateResolve getDefaultResolver() {
        return DEFAULT_RESOLVER;
    }

    /**
     * 使用 JEXL 表达式引擎解析模板（单个参数）
     *
     * @param template 模板字符串
     * @param key      参数名
     * @param value    参数值
     * @return 解析后的字符串
     */
    public static String jexlResolve(String template, String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return JexlUtils.evaluate(template, map);
    }

    /**
     * 使用 JEXL 表达式引擎解析模板（JSON 参数）
     *
     * @param template 模板字符串
     * @param content  JSON 格式的参数字符串
     * @return 解析后的字符串
     */
    public static String jexlResolve(String template, String content) {
        Map<String, Object> map = JSONObject.parseObject(content);
        return JexlUtils.evaluate(template, map);
    }

    /**
     * 使用单个键值对解析模板
     *
     * @param template 模板字符串
     * @param key      参数名
     * @param value    参数值
     * @return 解析后的字符串
     */
    public static String resolve(String template, String key, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        return resolve(template, jsonObject.toJSONString());
    }

    /**
     * 按顺序解析模板中的占位符
     * <p>
     * 例如：template = "category:${}:product:${}"，values = {"1", "2"}
     * 返回：category:1:product:2
     * </p>
     *
     * @param content 模板字符串
     * @param values  按顺序替换的值
     * @return 解析后的字符串
     */
    public static String resolve(String content, String... values) {
        int start = content.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        if (start == -1) {
            return content;
        }
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
     * 按顺序解析模板中的占位符（对象数组版本）
     *
     * @param content 模板字符串
     * @param values  按顺序替换的值
     * @return 解析后的字符串
     */
    public static String resolve(String content, Object[] values) {
        return resolve(content, Stream.of(values).map(String::valueOf).toArray(String[]::new));
    }

    /**
     * 根据自定义规则解析模板
     *
     * @param content 模板字符串
     * @param rule    解析规则回调函数，接收占位符名称，返回替换值
     * @return 解析后的字符串
     */
    public static String resolveByRule(String content, Function<String, String> rule) {
        int start = content.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        if (start == -1) {
            return content;
        }
        StringBuilder result = new StringBuilder(content);
        while (start != -1) {
            int end = result.indexOf(DEFAULT_PLACEHOLDER_SUFFIX, start);
            String placeholder = result.substring(start + DEFAULT_PLACEHOLDER_PREFIX.length(), end);
            String replaceContent = placeholder.trim().isEmpty() ? "" : rule.apply(placeholder);
            result.replace(start, end + DEFAULT_PLACEHOLDER_SUFFIX.length(), replaceContent);
            start = result.indexOf(DEFAULT_PLACEHOLDER_PREFIX, start + replaceContent.length());
        }
        return result.toString();
    }

    /**
     * 使用 Map 解析模板中的占位符
     * <p>
     * 例如：content = "product:${id}:detail:${did}"，valueMap = {id: 1, did: 2}
     * 返回：product:1:detail:2
     * </p>
     *
     * @param content  模板字符串
     * @param valueMap 参数映射
     * @return 解析后的字符串
     */
    public static String resolveByMap(String content, final Map<String, Object> valueMap) {
        return resolveByRule(content, placeholderValue -> String.valueOf(valueMap.get(placeholderValue)));
    }

    /**
     * 使用 Properties 解析模板中的占位符
     *
     * @param content    模板字符串
     * @param properties 属性配置
     * @return 解析后的字符串
     */
    public static String resolveByProperties(String content, final Properties properties) {
        return resolveByRule(content, properties::getProperty);
    }

    /**
     * 使用对象的字段路径解析模板
     * <p>
     * 支持嵌套属性访问，例如：${user.address.city}
     * </p>
     *
     * @param content 模板字符串
     * @param obj     填充对象（支持 Map 和 POJO）
     * @return 解析后的字符串
     */
    @SuppressWarnings("unchecked")
    public static String resolveByObject(String content, final Object obj) {
        if (obj instanceof Map) {
            return resolveByMap(content, (Map<String, Object>) obj);
        }
        return resolveByRule(content, placeholderValue -> 
                String.valueOf(ReflectionUtils.getValueByFieldPath(obj, placeholderValue)));
    }
}
