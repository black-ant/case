package com.gang.study.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CommonStringUtils 单元测试
 *
 * @author zengzg
 */
class CommonStringUtilsTest {

    @Test
    void testLineToHump() {
        assertEquals("userName", CommonStringUtils.lineToHump("user_name"));
        assertEquals("userId", CommonStringUtils.lineToHump("user_id"));
        assertEquals("createTime", CommonStringUtils.lineToHump("create_time"));
        assertEquals("id", CommonStringUtils.lineToHump("id"));
    }

    @Test
    void testHumpToLine() {
        assertEquals("user_name", CommonStringUtils.humpToLine("userName"));
        assertEquals("user_id", CommonStringUtils.humpToLine("userId"));
        assertEquals("create_time", CommonStringUtils.humpToLine("createTime"));
        assertEquals("id", CommonStringUtils.humpToLine("id"));
    }

    @Test
    void testHumpToLine2() {
        assertEquals("user_name", CommonStringUtils.humpToLine2("userName"));
        assertEquals("user_id", CommonStringUtils.humpToLine2("userId"));
        assertEquals("create_time", CommonStringUtils.humpToLine2("createTime"));
    }

    @Test
    void testLastVarchar() {
        assertEquals("txt", CommonStringUtils.lastVarchar("file.txt", '.'));
        assertEquals("test", CommonStringUtils.lastVarchar("/path/to/test", '/'));
    }

    @Test
    void testReplaceTemplateString() {
        String template = "Hello, ${name}!";
        String json = "{\"name\":\"World\"}";
        String result = CommonStringUtils.replaceTemplateString(template, json);
        assertEquals("Hello, World!", result);
    }
}

