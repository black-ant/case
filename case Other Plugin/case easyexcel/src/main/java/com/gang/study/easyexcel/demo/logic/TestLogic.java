package com.gang.study.easyexcel.demo.logic;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.gang.study.easyexcel.demo.listener.ConverterDataListener;
import com.gang.study.easyexcel.demo.listener.DemoTOListener;
import com.gang.study.easyexcel.demo.listener.UserIndexTOListener;
import com.gang.study.easyexcel.demo.listener.UserTOListener;
import com.gang.study.easyexcel.demo.to.ConverterData;
import com.gang.study.easyexcel.demo.to.DemoDataTO;
import com.gang.study.easyexcel.demo.to.UserIndexTO;
import com.gang.study.easyexcel.demo.to.UserTO;
import com.gang.study.easyexcel.demo.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/4/12 23:26
 * @Created by zengzg
 */
@Component
public class TestLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> run testLogic <-------");
//        simpleRead();

//        indexOrNameRead();

//        repeatedRead();

        converterRead();
    }

    public static String encode(String path) {
        return path.replaceAll("%20", " ");
    }


    /**
     * 最简单的读
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link UserTO}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UserTOListener}
     * <p>
     * 3. 直接读即可
     */
    public void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = encode(FileUtils.getPath() + "demo.xlsx");
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭 ,sheet 以 0 开始
        EasyExcel.read(fileName, UserTO.class, new UserTOListener()).sheet(1).doRead();

        // 写法2：
        fileName = encode(FileUtils.getPath() + "demo.xlsx");
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, UserTO.class, new UserTOListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(1).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    /**
     * 指定列的下标或者列名
     *
     * <p>
     * 1. 创建excel对应的实体对象,并使用{@link }注解. 参照{@link UserIndexTO}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UserIndexTOListener}
     * <p>
     * 3. 直接读即可
     */
    public void indexOrNameRead() {
        String fileName = encode(FileUtils.getPath() + "demo.xlsx");
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, UserIndexTO.class, new UserIndexTOListener()).sheet(1).doRead();
    }

    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link UserTO}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UserTOListener}
     * <p>
     * 3. 直接读即可
     */
    public void repeatedRead() {
        String fileName = encode(FileUtils.getPath() + "demo.xlsx");
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(fileName, UserTO.class, new UserTOListener()).doReadAll();

        // 读取部分sheet
        fileName = encode(FileUtils.getPath() + "demo.xlsx");
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName).build();

            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(0).head(DemoDataTO.class).registerReadListener(new DemoTOListener()).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(1).head(UserTO.class).registerReadListener(new UserTOListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    /**
     * 日期、数字或者自定义格式转换
     * <p>
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ConverterData}.里面可以使用注解{@link }、{@link }或者自定义注解
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link ConverterDataListener}
     * <p>
     * 3. 直接读即可
     */
    public void converterRead() {
        String fileName = encode(FileUtils.getPath() + "demo.xlsx");
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ConverterData.class, new ConverterDataListener())
                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
                // .registerConverter(new CustomStringStringConverter())
                // 读取sheet
                .sheet(1).doRead();
    }

//
//    /**
//     * 多行头
//     *
//     * <p>
//     * 1. 创建excel对应的实体对象 参照{@link DemoData}
//     * <p>
//     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
//     * <p>
//     * 3. 设置headRowNumber参数，然后读。 这里要注意headRowNumber如果不指定， 会根据你传入的class的{@link ExcelProperty#value()}里面的表头的数量来决定行数，
//     * 如果不传入class则默认为1.当然你指定了headRowNumber不管是否传入class都是以你传入的为准。
//     */
//    public void complexHeaderRead() {
//        String fileName = FileUtils.getPath() + "demo" + File.separator + "demo.xlsx";
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
//        EasyExcel.read(fileName, UserTO.class, new DemoDataListener()).sheet()
//                // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
//                .headRowNumber(1).doRead();
//    }
//
//    /**
//     * 读取表头数据
//     *
//     * <p>
//     * 1. 创建excel对应的实体对象 参照{@link DemoData}
//     * <p>
//     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoHeadDataListener}
//     * <p>
//     * 3. 直接读即可
//     */
//    public void headerRead() {
//        String fileName = FileUtils.getPath() + "demo" + File.separator + "demo.xlsx";
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
//        EasyExcel.read(fileName, UserTO.class, new DemoHeadDataListener()).sheet().doRead();
//    }
//
//    /**
//     * 额外信息（批注、超链接、合并单元格信息读取）
//     * <p>
//     * 由于是流式读取，没法在读取到单元格数据的时候直接读取到额外信息，所以只能最后通知哪些单元格有哪些额外信息
//     *
//     * <p>
//     * 1. 创建excel对应的实体对象 参照{@link DemoExtraData}
//     * <p>
//     * 2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoExtraListener}
//     * <p>
//     * 3. 直接读即可
//     *
//     * @since 2.2.0-beat1
//     */
//    public void extraRead() {
//        String fileName = FileUtils.getPath() + "demo" + File.separator + "extra.xlsx";
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
//        EasyExcel.read(fileName, DemoExtraData.class, new DemoExtraListener())
//                // 需要读取批注 默认不读取
//                .extraRead(CellExtraTypeEnum.COMMENT)
//                // 需要读取超链接 默认不读取
//                .extraRead(CellExtraTypeEnum.HYPERLINK)
//                // 需要读取合并单元格信息 默认不读取
//                .extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
//    }
//
//    /**
//     * 读取公式和单元格类型
//     *
//     * <p>
//     * 1. 创建excel对应的实体对象 参照{@link CellDataReadDemoData}
//     * <p>
//     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoHeadDataListener}
//     * <p>
//     * 3. 直接读即可
//     *
//     * @since 2.2.0-beat1
//     */
//    public void cellDataRead() {
//        String fileName = FileUtils.getPath() + "demo" + File.separator + "cellDataDemo.xlsx";
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
//        EasyExcel.read(fileName, CellDataReadDemoData.class, new CellDataDemoHeadDataListener()).sheet().doRead();
//    }
//
//    /**
//     * 数据转换等异常处理
//     *
//     * <p>
//     * 1. 创建excel对应的实体对象 参照{@link ExceptionDemoData}
//     * <p>
//     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoExceptionListener}
//     * <p>
//     * 3. 直接读即可
//     */
//    public void exceptionRead() {
//        String fileName = FileUtils.getPath() + "demo" + File.separator + "demo.xlsx";
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
//        EasyExcel.read(fileName, ExceptionDemoData.class, new DemoExceptionListener()).sheet().doRead();
//    }
//
//    /**
//     * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
//     */
//    public void synchronousRead() {
//        String fileName = FileUtils.getPath() + "demo" + File.separator + "demo.xlsx";
//        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
//        List<UserTO> list = EasyExcel.read(fileName).head(UserTO.class).sheet().doReadSync();
//        for (UserTO data : list) {
//            logger.info("读取到数据:{}", JSON.toJSONString(data));
//        }
//
//        // 这里 也可以不指定class，返回一个list，然后读取第一个sheet 同步读取会自动finish
//        List<Map<Integer, String>> listMap = EasyExcel.read(fileName).sheet().doReadSync();
//        for (Map<Integer, String> data : listMap) {
//            // 返回每条数据的键值对 表示所在的列 和所在列的值
//            logger.info("读取到数据:{}", JSON.toJSONString(data));
//        }
//    }
//
//    /**
//     * 不创建对象的读
//     */
//    public void noModelRead() {
//        String fileName = FileUtils.getPath() + "demo" + File.separator + "demo.xlsx";
//        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
//        EasyExcel.read(fileName, new NoModelDataListener()).sheet().doRead();
//    }
}
