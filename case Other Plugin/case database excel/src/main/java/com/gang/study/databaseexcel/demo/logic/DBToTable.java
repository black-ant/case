package com.gang.study.databaseexcel.demo.logic;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * @Classname DBToTable
 * @Description 导出数据库设计结构为Excel表格
 * @Date 2020/5/13 11:33
 * @Created by zengzg
 */
public class DBToTable {

    protected static void main(DBInfo info) throws Exception {
        Class.forName(info.getDriver());
        Connection conn = DriverManager.getConnection(info.getUrl(), info.getUser(), info.getPassword());
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        ResultSet tables = databaseMetaData.getTables(null, "PUBLIC", "%", new String[]{"TABLE"});
        String path = "E://数据库设计.xls";
        WritableWorkbook book = Workbook.createWorkbook(new File(path));
        // 生成名为“第一页”的工作表，参数0表示这是第一页
        WritableSheet sheet = book.createSheet("第一页 ", 0);
        int i = 0;
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            ResultSet columns = databaseMetaData.getColumns(null, "%", tableName, "%");
            /**
             * COLUMN_NAME String => 列名称
             DATA_TYPE int => 来自 java.sql.Types 的 SQL 类型
             COLUMN_SIZE int => 列的大小。
             REMARKS String => 描述列的注释
             IS_NULLABLE String => ISO 规则用于确定列是否包括 null。
             YES --- 如果参数可以包括 NULL
             NO --- 如果参数不可以包括 NULL
             */
            sheet.mergeCells(0, i, 5, i + 1);
            WritableCellFormat format = new WritableCellFormat();
            format.setAlignment(Alignment.CENTRE);
            format.setVerticalAlignment(VerticalAlignment.CENTRE);
            Label label = new Label(0, i, "表名" + tableName, format);
            i += 2;
            Label label2 = new Label(0, i, "字段名", format);
            sheet.addCell(label2);
            Label label3 = new Label(1, i, "属性名", format);
            sheet.addCell(label3);
            Label label4 = new Label(2, i, "类型", format);
            sheet.addCell(label4);
            Label label5 = new Label(3, i, "长度", format);
            sheet.addCell(label5);
            Label label6 = new Label(4, i, "是否可空", format);
            sheet.addCell(label6);
            Label label7 = new Label(5, i, "注解", format);
            sheet.addCell(label7);
            sheet.addCell(label);
            while (columns.next()) {
                ++i;
                //列明
                String colName = columns.getString("COLUMN_NAME");
                Label C = new Label(0, i, colName, format);
                sheet.addCell(C);
                //注解-属性名
                String remarks = columns.getString("REMARKS");
                Label R = new Label(1, i, remarks, format);
                sheet.addCell(R);
                //数据类型-数字
                int type = columns.getInt("DATA_TYPE");
                //数据类型-字符串
                String typeName = Types.MAP.get(type);
                Label T = new Label(2, i, typeName, format);
                sheet.addCell(T);
                //数据长度
                int length = columns.getInt("COLUMN_SIZE");
                Label L = new Label(3, i, length + "", format);
                sheet.addCell(L);
                //是否为空
                String isNull = columns.getString("IS_NULLABLE");
                isNull = "YES".equals(isNull) ? "NULL" : "NOT NULL";
                Label N = new Label(4, i, isNull, format);
                sheet.addCell(N);
                Label RR = new Label(5, i, remarks, format);
                sheet.addCell(RR);
            }
            i += 3;
        }
        // 写入数据并关闭文件
        book.write();
        book.close();
    }
}
