package com.gang.study.utils;

import java.util.Random;
import java.util.UUID;

/**
 * ID 生成工具类
 * <p>
 * 提供各种随机 ID 和测试数据的生成功能。
 * </p>
 *
 * <h3>使用示例：</h3>
 * <pre>{@code
 * String id = IDUtils.crateId();           // 随机6位数字字符串
 * String email = IDUtils.createEmail();    // 随机邮箱
 * String mobile = IDUtils.createMobil();   // 随机手机号
 * String uuid = IDUtils.createUUID();      // UUID
 * }</pre>
 *
 * @author zengzg
 * @since 2020/1/12
 */
public final class IDUtils {

    /** 默认随机数范围 */
    private static final Integer RANDOM_SIZE = 999999;
    
    /** 随机数生成器 */
    private static final Random RANDOM = new Random();

    private IDUtils() {
        // 工具类禁止实例化
    }

    /**
     * 生成随机 ID 字符串（默认6位）
     *
     * @return 随机数字字符串
     */
    public static String crateId() {
        return crateId(RANDOM_SIZE);
    }

    /**
     * 生成指定范围内的随机 ID 字符串
     *
     * @param size 随机数上限
     * @return 随机数字字符串
     */
    public static String crateId(Integer size) {
        return String.valueOf(createId(size));
    }

    /**
     * 生成指定范围内的随机整数
     *
     * @param size 随机数上限
     * @return 随机整数
     */
    public static Integer createId(Integer size) {
        return RANDOM.nextInt(size);
    }

    /**
     * 生成随机邮箱地址（用于测试）
     *
     * @return 随机邮箱地址
     */
    public static String createEmail() {
        return crateId() + "@qgang.com";
    }

    /**
     * 生成随机手机号（用于测试）
     *
     * @return 随机手机号
     */
    public static String createMobil() {
        return "123046400" + crateId(99);
    }

    /**
     * 生成 UUID（去除横线）
     *
     * @return 32位 UUID 字符串
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成标准 UUID
     *
     * @return 36位标准 UUID 字符串
     */
    public static String createStandardUUID() {
        return UUID.randomUUID().toString();
    }
}
