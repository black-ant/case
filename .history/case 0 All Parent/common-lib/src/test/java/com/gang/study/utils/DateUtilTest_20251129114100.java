package com.gang.study.utils;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DateUtil 单元测试
 *
 * @author zengzg
 */
class DateUtilTest {

    @Test
    void testFormatDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 15, 0, 0, 0);
        Date date = cal.getTime();
        
        String result = DateUtil.formatDate(date);
        assertEquals("2024-01-15", result);
    }

    @Test
    void testFormatDateTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 15, 12, 30, 45);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        
        String result = DateUtil.formatDateTime(date);
        assertEquals("2024-01-15 12:30:45", result);
    }

    @Test
    void testParseDate() {
        Date result = DateUtil.parseDate("2024-01-15");
        assertNotNull(result);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(2024, cal.get(Calendar.YEAR));
        assertEquals(Calendar.JANUARY, cal.get(Calendar.MONTH));
        assertEquals(15, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    void testParseDateTime() {
        Date result = DateUtil.parseDateTime("2024-01-15 12:30:45");
        assertNotNull(result);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        assertEquals(12, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, cal.get(Calendar.MINUTE));
        assertEquals(45, cal.get(Calendar.SECOND));
    }

    @Test
    void testParseInvalidDate() {
        Date result = DateUtil.parseDate("invalid-date");
        assertNull(result);
    }

    @Test
    void testAddDays() {
        Date today = new Date();
        Date tomorrow = DateUtil.addDays(today, 1);
        Date yesterday = DateUtil.addDays(today, -1);
        
        assertNotNull(tomorrow);
        assertNotNull(yesterday);
        assertTrue(tomorrow.after(today));
        assertTrue(yesterday.before(today));
    }

    @Test
    void testAddMonths() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 15);
        Date date = cal.getTime();
        
        Date result = DateUtil.addMonths(date, 1);
        cal.setTime(result);
        assertEquals(Calendar.FEBRUARY, cal.get(Calendar.MONTH));
    }

    @Test
    void testAddYears() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 15);
        Date date = cal.getTime();
        
        Date result = DateUtil.addYears(date, 1);
        cal.setTime(result);
        assertEquals(2025, cal.get(Calendar.YEAR));
    }

    @Test
    void testAddDaysWithNull() {
        Date result = DateUtil.addDays(null, 1);
        assertNull(result);
    }
}

