package com.gang.study.easyexcel.demo.to;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

/**
 * @Classname UserIndexTO
 * @Description TODO
 * @Date 2021/2/15 16:18
 * @Created by zengzg
 */
public class UserIndexTO {

    /**
     * 强制读取第三个 这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
     * 字符串从0 开始
     */
    @ExcelProperty(index = 0)
    private Integer doubleData;
    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty("username")
    private String username;

    @ExcelProperty("date")
    private Date date;


    public Integer getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Integer doubleData) {
        this.doubleData = doubleData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
