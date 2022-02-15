package com.gang.study.canal.demo.service;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname CanalTemplate
 * @Description TODO
 * @Date 2021/11/16
 * @Created by zengzg
 */
//@Service
public class CanalTemplate implements ApplicationRunner {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static int BATCH_SIZE = 1000;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> Canal 请求案例 <-------");
        getCanalInfo();
    }

    /**
     *
     */
    public void getCanalInfo() throws Exception {

        // Step 1 : 建立 Socket 连接
        // 此处包含三种创建方式 : newSingleConnector / newClusterConnector / newClusterConnector
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1", 11111), "example", "", "");
//        CanalConnector connector1 = CanalConnectors.newClusterConnector("127.0.0.1:2181", "example", "", "");
//        CanalConnector connector2 = CanalConnectors.newClusterConnector(Arrays.asList(new InetSocketAddress(AddressUtils.getHostIp(), 11111)), "example", "", "");
        // Step 2 : 打开连接
        connector.connect();

        // Step 3 : 配置扫描路径
        connector.subscribe(".*\\..*");

        // 回滚到未进行ack的地方，下次fetch的时候，可以从最后一个没有ack的地方开始拿
        connector.rollback();

        while (true) {
            // 获取指定数量的数据
            Message message = connector.getWithoutAck(BATCH_SIZE);
            //获取批量ID
            long batchId = message.getId();
            //获取批量的数量
            int size = message.getEntries().size();
            //如果没有数据
            if (batchId == -1 || size == 0) {
                Thread.sleep(2000);
            } else {
                //如果有数据,处理数据
                printEntry(message.getEntries());
            }
            //进行 batch id 的确认。确认之后，小于等于此 batchId 的 Message 都会被确认。
            connector.ack(batchId);
        }
    }


    /**
     * 打印canal server解析binlog获得的实体类信息
     */
    private static void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                //开启/关闭事务的实体类型，跳过
                continue;
            }
            //RowChange对象，包含了一行数据变化的所有特征
            //比如isDdl 是否是ddl变更操作 sql 具体的ddl sql beforeColumns afterColumns 变更前后的数据字段等等
            RowChange rowChage;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
            }
            //获取操作类型：insert/update/delete类型
            CanalEntry.EventType eventType = rowChage.getEventType();

            //打印Header信息
            System.out.println(String.format("================》; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            //判断是否是DDL语句
            if (rowChage.getIsDdl()) {
                System.out.println("================》;isDdl: true,sql:" + rowChage.getSql());
            }

            //获取RowChange对象里的每一行数据，打印出来
            for (RowData rowData : rowChage.getRowDatasList()) {
                //如果是删除语句
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                    //如果是新增语句
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                    //如果是更新的语句
                } else {
                    //变更前的数据
                    System.out.println("------->; before");
                    printColumn(rowData.getBeforeColumnsList());
                    //变更后的数据
                    System.out.println("------->; after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}
