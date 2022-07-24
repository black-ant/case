package com.gang.study.easyexcel.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EasyExcelTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testRead() {
        logger.info("读取指定的文件");
        String fileName = "C:\\Users\\10169\\Desktop\\ant_user.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行
        EasyExcel.read(fileName, DemoBean.class, new AnalysisEventListener<DemoBean>() {
            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            private List<DemoBean> cachedDataList = new ArrayList<>();

            @Override
            public void invoke(DemoBean data, AnalysisContext context) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = new ArrayList<>();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                logger.info("{}条数据，开始存储数据库！", cachedDataList.size());
                logger.info("存储数据库成功！");
            }
        }).sheet().doRead();

        logger.info("测试是否异步");
    }

}

