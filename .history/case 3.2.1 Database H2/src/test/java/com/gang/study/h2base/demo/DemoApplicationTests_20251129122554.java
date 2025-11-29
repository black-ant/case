package com.gang.study.h2base.demo;

import com.gang.study.h2base.demo.logic.H2BaseMemeryLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * H2 数据库测试类
 *
 * @author zengzg
 */
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private H2BaseMemeryLogic h2Logic;

    @Test
    void contextLoads() {
        assertNotNull(h2Logic);
    }

    @Test
    void testH2MemoryOperations() throws Exception {
        // 启动服务器
        h2Logic.startServer();
        assertTrue(h2Logic.isRunning());
        
        // 执行数据操作
        h2Logic.insertOperation();
        
        // 验证数据
        h2Logic.checkH2Logic();
        
        // 关闭服务器
        h2Logic.stopServer();
        assertFalse(h2Logic.isRunning());
    }
}
