package com.gang.study.io.demo.service;

import com.gang.study.io.demo.model.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class InputOutputService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    String path = "D:\\java\\workspace\\git\\case\\case Java Other\\case Java IO\\doc\\test.txt";
    String dest = "D:\\java\\workspace\\git\\case\\case Java Other\\case Java IO\\doc\\test1.txt";

    public void run() {
        // read
//        readStream();

        // copy file
        try {
            copyFile(path, dest);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Stram 读流
     */
    public void readStream() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(Properties.PATH));
            StringBuffer backMsg = new StringBuffer();
            while (inputStream.read() != -1) {
                backMsg.append(inputStream.read());
            }

            logger.info("----> msg is :{}", backMsg.toString());

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * copy file from path to dest
     *
     * @param path from
     * @param dest to
     */
    public String copyFile(String path, String dest) throws IOException, ClassNotFoundException {
        File srcFile = new File(path);//源文件数据源
        File desFile = new File(dest);//写入到目标数据源
        //数据源不存在
        if (!srcFile.exists() || !desFile.exists()) {
            throw new ClassNotFoundException("源文件或者拷贝目标文件地址不存在！");
        }
        //非文件类型
        if (!srcFile.isFile() || !desFile.isFile()) {
            return "源文件或者目标文件不是文件类型!";
        }
        InputStream is = null;
        OutputStream os = null;
        byte[] buf = new byte[1024];//缓存区
        int len = 0;//读取长度
        try {
            is = new BufferedInputStream(new FileInputStream(srcFile));//读取数据源
            os = new BufferedOutputStream(new FileOutputStream(desFile));//写入到数据源
            while ((len = is.read(buf)) != -1) { //读取长度不为-1，继续读取
                os.write(buf); //读取内容之后马上写入目标数据源
            }
            os.flush();//输出
            return "文件拷贝成功！查看拷贝文件路径：" + desFile.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                is.close();
            if (os != null)
                os.close();
        }
        return "文件拷贝失败";
    }

    /**
     * stream 写方法
     */
    public void writeStream() {

    }
}
