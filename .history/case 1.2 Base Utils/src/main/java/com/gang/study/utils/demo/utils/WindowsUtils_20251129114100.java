package com.gang.study.utils.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Windows/系统工具类
 * <p>
 * 提供与操作系统交互的工具方法，包括：
 * <ul>
 *     <li>获取系统信息</li>
 *     <li>执行系统命令</li>
 *     <li>文件系统操作</li>
 * </ul>
 * </p>
 * 
 * <h3>使用示例：</h3>
 * <pre>{@code
 * // 获取操作系统名称
 * String osName = WindowsUtils.getOsName();
 * 
 * // 判断是否为 Windows 系统
 * boolean isWindows = WindowsUtils.isWindows();
 * 
 * // 执行命令
 * String result = WindowsUtils.executeCommand("dir");
 * }</pre>
 *
 * @author zengzg
 * @since 2020/7/31
 */
@Component
public class WindowsUtils implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(WindowsUtils.class);

    /**
     * 应用启动后执行
     * <p>
     * 打印系统信息用于演示
     * </p>
     *
     * @param args 应用参数
     */
    @Override
    public void run(ApplicationArguments args) {
        logger.info("=== System Information ===");
        logger.info("OS Name: {}", getOsName());
        logger.info("OS Version: {}", getOsVersion());
        logger.info("OS Arch: {}", getOsArch());
        logger.info("Java Version: {}", getJavaVersion());
        logger.info("User Home: {}", getUserHome());
        logger.info("Working Directory: {}", getWorkingDirectory());
        logger.info("===========================");
    }

    /**
     * 获取操作系统名称
     *
     * @return 操作系统名称，如 "Windows 10"、"Linux"
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * 获取操作系统版本
     *
     * @return 操作系统版本号
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * 获取操作系统架构
     *
     * @return 系统架构，如 "amd64"、"x86"
     */
    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    /**
     * 获取 Java 版本
     *
     * @return Java 版本号
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * 获取用户主目录
     *
     * @return 用户主目录路径
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * 获取当前工作目录
     *
     * @return 当前工作目录路径
     */
    public static String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * 判断是否为 Windows 操作系统
     *
     * @return true 如果是 Windows 系统
     */
    public static boolean isWindows() {
        return getOsName().toLowerCase().contains("windows");
    }

    /**
     * 判断是否为 Linux 操作系统
     *
     * @return true 如果是 Linux 系统
     */
    public static boolean isLinux() {
        return getOsName().toLowerCase().contains("linux");
    }

    /**
     * 判断是否为 Mac 操作系统
     *
     * @return true 如果是 Mac 系统
     */
    public static boolean isMac() {
        return getOsName().toLowerCase().contains("mac");
    }

    /**
     * 执行系统命令
     *
     * @param command 要执行的命令
     * @return 命令输出结果
     */
    public static String executeCommand(String command) {
        StringBuilder result = new StringBuilder();
        
        try {
            Process process;
            if (isWindows()) {
                process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", command});
            } else {
                process = Runtime.getRuntime().exec(new String[]{"sh", "-c", command});
            }
            
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), 
                            isWindows() ? Charset.forName("GBK") : Charset.defaultCharset()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.lineSeparator());
                }
            }
            
            process.waitFor();
            
        } catch (Exception e) {
            logger.error("Failed to execute command: {}", command, e);
            return "Error: " + e.getMessage();
        }
        
        return result.toString().trim();
    }

    /**
     * 获取所有系统属性
     *
     * @return 系统属性对象
     */
    public static Properties getSystemProperties() {
        return System.getProperties();
    }

    /**
     * 获取可用处理器数量
     *
     * @return 可用处理器（CPU 核心）数量
     */
    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取 JVM 最大内存（MB）
     *
     * @return 最大内存大小
     */
    public static long getMaxMemoryMB() {
        return Runtime.getRuntime().maxMemory() / (1024 * 1024);
    }

    /**
     * 获取 JVM 已分配内存（MB）
     *
     * @return 已分配内存大小
     */
    public static long getTotalMemoryMB() {
        return Runtime.getRuntime().totalMemory() / (1024 * 1024);
    }

    /**
     * 获取 JVM 空闲内存（MB）
     *
     * @return 空闲内存大小
     */
    public static long getFreeMemoryMB() {
        return Runtime.getRuntime().freeMemory() / (1024 * 1024);
    }

    /**
     * 检查文件是否存在
     *
     * @param path 文件路径
     * @return true 如果文件存在
     */
    public static boolean fileExists(String path) {
        return new File(path).exists();
    }

    /**
     * 检查路径是否为目录
     *
     * @param path 路径
     * @return true 如果是目录
     */
    public static boolean isDirectory(String path) {
        return new File(path).isDirectory();
    }
}
