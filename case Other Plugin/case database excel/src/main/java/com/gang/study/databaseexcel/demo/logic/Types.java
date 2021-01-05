package com.gang.study.databaseexcel.demo.logic;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ExcelLogic
 * @Description TODO
 * @Date 2020/5/13 11:31
 * @Created by zengzg
 */
@Component
public class Types {

    protected final static Map<Integer, String> MAP = new HashMap<Integer, String>();
    public static final int ARRAY = 2003;
    public static final int BIGINT = -5;
    public static final int BINARY = -2;
    public static final int BIT = -7;
    public static final int BLOB = 2004;
    public static final int BOOLEAN = 16;
    public static final int CHAR = 1;
    public static final int CLOB = 2005;
    public static final int DATALINK = 70;
    public static final int DATE = 91;
    public static final int DECIMAL = 3;
    public static final int DISTINCT = 2001;
    public static final int DOUBLE = 8;
    public static final int FLOAT = 6;
    public static final int INTEGER = 4;
    public static final int JAVA_OBJECT = 2000;
    public static final int LONGNVARCHAR = -16;
    public static final int LONGVARBINARY = -4;
    public static final int LONGVARCHAR = -1;
    public static final int NCHAR = -15;
    public static final int NCLOB = 2011;
    public static final int NULL = 0;
    public static final int NUMERIC = 2;
    public static final int NVARCHAR = -9;
    public static final int OTHER = 1111;
    public static final int REAL = 7;
    public static final int REF = 2006;
    public static final int ROWID = -8;
    public static final int SMALLINT = 5;
    public static final int SQLXML = 2009;
    public static final int STRUCT = 2002;
    public static final int TIME = 92;
    public static final int TIMESTAMP = 93;
    public static final int TINYINT = -6;
    public static final int VARBINARY = -3;
    public static final int VARCHAR = 12;

    /**
     * 根据需要添加数字与数据类型字符串的对应关系
     */
    static {
        MAP.put(12, "VARCHAR");
        MAP.put(4, "INTEGER");
        MAP.put(2005, "CLOB");
        MAP.put(2004, "BLOB");
        MAP.put(91, "DATE");
        MAP.put(-5, "BIGINT");
        MAP.put(1, "CHAR");
        MAP.put(93, "TIMESTAMP");
        MAP.put(2, "NUMERIC");
        MAP.put(5, "SMALLINT");
    }
}


