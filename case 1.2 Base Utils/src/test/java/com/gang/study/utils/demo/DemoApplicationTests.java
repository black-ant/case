package com.gang.study.utils.demo;

import com.gang.study.utils.demo.utils.WindowsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 应用测试类
 *
 * @author zengzg
 */
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private WindowsUtils windowsUtils;

    @Test
    void contextLoads() {
        assertNotNull(windowsUtils);
    }

    @Test
    void testGetOsName() {
        String osName = WindowsUtils.getOsName();
        assertNotNull(osName);
        assertFalse(osName.isEmpty());
        System.out.println("OS Name: " + osName);
    }

    @Test
    void testGetJavaVersion() {
        String javaVersion = WindowsUtils.getJavaVersion();
        assertNotNull(javaVersion);
        assertFalse(javaVersion.isEmpty());
        System.out.println("Java Version: " + javaVersion);
    }

    @Test
    void testIsOperatingSystem() {
        // 至少有一个应该为 true
        boolean isWindows = WindowsUtils.isWindows();
        boolean isLinux = WindowsUtils.isLinux();
        boolean isMac = WindowsUtils.isMac();
        
        assertTrue(isWindows || isLinux || isMac, 
                "Should detect at least one operating system");
    }

    @Test
    void testGetAvailableProcessors() {
        int processors = WindowsUtils.getAvailableProcessors();
        assertTrue(processors > 0);
        System.out.println("Available Processors: " + processors);
    }

    @Test
    void testMemoryInfo() {
        long maxMemory = WindowsUtils.getMaxMemoryMB();
        long totalMemory = WindowsUtils.getTotalMemoryMB();
        long freeMemory = WindowsUtils.getFreeMemoryMB();
        
        assertTrue(maxMemory > 0);
        assertTrue(totalMemory > 0);
        assertTrue(freeMemory >= 0);
        assertTrue(maxMemory >= totalMemory);
        
        System.out.println("Max Memory: " + maxMemory + " MB");
        System.out.println("Total Memory: " + totalMemory + " MB");
        System.out.println("Free Memory: " + freeMemory + " MB");
    }

    @Test
    void testGetWorkingDirectory() {
        String workDir = WindowsUtils.getWorkingDirectory();
        assertNotNull(workDir);
        assertTrue(WindowsUtils.isDirectory(workDir));
    }

    @Test
    void testGetUserHome() {
        String userHome = WindowsUtils.getUserHome();
        assertNotNull(userHome);
        assertTrue(WindowsUtils.fileExists(userHome));
    }

    @Test
    void testFileExists() {
        // 当前目录应该存在
        assertTrue(WindowsUtils.fileExists("."));
        
        // 随机名称的文件应该不存在
        assertFalse(WindowsUtils.fileExists("nonexistent_file_12345.xyz"));
    }
}
