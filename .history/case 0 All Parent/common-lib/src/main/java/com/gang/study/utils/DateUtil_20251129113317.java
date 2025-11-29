package com.gang.study.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期时间工具类
 * <p>
 * 提供线程安全的日期格式化和解析功能。
 * 使用 ThreadLocal 缓存 DateFormat 实例，避免多线程问题。
 * </p>
 *
 * <h3>使用示例：</h3>
 * <pre>{@code
 * // 格式化日期
 * String dateStr = DateUtil.formatDate(new Date());      // 2024-01-01
 * String dateTimeStr = DateUtil.formatDateTime(new Date()); // 2024-01-01 12:00:00
 *
 * // 解析日期
 * Date date = DateUtil.parseDate("2024-01-01");
 * Date dateTime = DateUtil.parseDateTime("2024-01-01 12:00:00");
 *
 * // 日期计算
 * Date tomorrow = DateUtil.addDays(new Date(), 1);
 * Date nextMonth = DateUtil.addMonths(new Date(), 1);
 * }</pre>
 *
 * @author zengzg
 * @since 1.0
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /** 日期格式：yyyy-MM-dd */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** 日期时间格式：yyyy-MM-dd HH:mm:ss */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 线程本地变量，缓存 DateFormat 实例以保证线程安全 */
    private static final ThreadLocal<Map<String, DateFormat>> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<>();

    private DateUtil() {
        // 工具类禁止实例化
    }

    /**
     * 获取指定格式的 DateFormat 实例（线程安全）
     *
     * @param pattern 日期格式模式
     * @return DateFormat 实例
     * @throws IllegalArgumentException 如果 pattern 为空
     */
    private static DateFormat getDateFormat(String pattern) {
        if (pattern == null || pattern.trim().isEmpty()) {
            throw new IllegalArgumentException("pattern cannot be empty.");
        }

        Map<String, DateFormat> dateFormatMap = DATE_FORMAT_THREAD_LOCAL.get();
        if (dateFormatMap != null && dateFormatMap.containsKey(pattern)) {
            return dateFormatMap.get(pattern);
        }

        synchronized (DATE_FORMAT_THREAD_LOCAL) {
            if (dateFormatMap == null) {
                dateFormatMap = new HashMap<>();
            }
            dateFormatMap.put(pattern, new SimpleDateFormat(pattern));
            DATE_FORMAT_THREAD_LOCAL.set(dateFormatMap);
        }

        return dateFormatMap.get(pattern);
    }

    /**
     * 将日期格式化为 "yyyy-MM-dd" 格式的字符串
     *
     * @param date 日期对象
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date date) {
        return format(date, DATE_FORMAT);
    }

    /**
     * 将日期格式化为 "yyyy-MM-dd HH:mm:ss" 格式的字符串
     *
     * @param date 日期对象
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(Date date) {
        return format(date, DATETIME_FORMAT);
    }

    /**
     * 按照指定格式将日期格式化为字符串
     *
     * @param date   日期对象
     * @param pattern 日期格式模式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        return getDateFormat(pattern).format(date);
    }

    /**
     * 解析 "yyyy-MM-dd" 格式的日期字符串
     *
     * @param dateString 日期字符串
     * @return 解析后的 Date 对象，解析失败返回 null
     */
    public static Date parseDate(String dateString) {
        return parse(dateString, DATE_FORMAT);
    }

    /**
     * 解析 "yyyy-MM-dd HH:mm:ss" 格式的日期时间字符串
     *
     * @param dateString 日期时间字符串
     * @return 解析后的 Date 对象，解析失败返回 null
     */
    public static Date parseDateTime(String dateString) {
        return parse(dateString, DATETIME_FORMAT);
    }

    /**
     * 按照指定格式解析日期字符串
     *
     * @param dateString 日期字符串
     * @param pattern    日期格式模式
     * @return 解析后的 Date 对象，解析失败返回 null
     */
    public static Date parse(String dateString, String pattern) {
        try {
            return getDateFormat(pattern).parse(dateString);
        } catch (Exception e) {
            logger.warn("parse date error, dateString = {}, pattern={}; errorMsg = {}", 
                    dateString, pattern, e.getMessage());
            return null;
        }
    }

    /**
     * 在指定日期上增加/减少天数
     *
     * @param date   基准日期
     * @param amount 增加的天数（负数表示减少）
     * @return 计算后的日期，如果 date 为 null 则返回 null
     */
    public static Date addDays(final Date date, final int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 在指定日期上增加/减少年数
     *
     * @param date   基准日期
     * @param amount 增加的年数（负数表示减少）
     * @return 计算后的日期，如果 date 为 null 则返回 null
     */
    public static Date addYears(final Date date, final int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 在指定日期上增加/减少月数
     *
     * @param date   基准日期
     * @param amount 增加的月数（负数表示减少）
     * @return 计算后的日期，如果 date 为 null 则返回 null
     */
    public static Date addMonths(final Date date, final int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 通用日期加减方法
     *
     * @param date          基准日期
     * @param calendarField Calendar 字段常量
     * @param amount        增加的数量（负数表示减少）
     * @return 计算后的日期
     */
    private static Date add(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            return null;
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
}
