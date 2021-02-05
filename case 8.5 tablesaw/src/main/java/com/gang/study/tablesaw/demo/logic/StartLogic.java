package com.gang.study.tablesaw.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.numbers.NumberColumnFormatter;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.AreaPlot;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/7/21 15:11
 * @Created by zengzg
 */
@Component
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        createTable();

//        loadVCsv();
        showTable();
    }


    /**
     * 创建表格
     */
    public void createTable() {
        String[] students = {"小明", "李雷", "小二"};
        double[] scores = {90.1, 84.3, 99.7};
        Table table = Table.create("学生分数统计表").addColumns(
                StringColumn.create("姓名", students),
                DoubleColumn.create("分数", scores));

        logger.info("{} ", table.print());
    }

    /**
     * 加载 csv 文件
     */
    public void loadVCsv() throws Exception {
        Table table = Table.read().csv("C:\\Users\\10169\\Desktop\\test.csv");
        Table whoPercents = table.xTabPercents("name");
        whoPercents.columnsOfType(ColumnType.DOUBLE)
                .forEach(x -> ((NumberColumn) x).setPrintFormatter(
                        NumberColumnFormatter.percent(0)));
        System.out.println(whoPercents.toString());
    }

    /**
     *
     */
    public void showTable() throws Exception {
        Table robberies = Table.read().csv("C:\\Users\\10169\\Desktop\\test.csv");
        Plot.show(
                AreaPlot.create(
                        "Boston Robberies by month: Jan 1966-Oct 1975",
                        robberies, "date", "num"));
    }
}
