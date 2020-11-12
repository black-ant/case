package com.gang.study.spring.fileupload.demo.controller;

import com.gang.study.spring.fileupload.demo.model.DownloadRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;

/**
 * @Classname FileDownLoadController
 * @Description TODO
 * @Date 2020/11/12 16:16
 * @Created by zengzg
 */
@Controller
@RequestMapping("/download")
public class FileDownLoadController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RequestContextHolder context;

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/getOne")
    public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
        File resource = resourceLoader.getResource("classpath:download/test.txt").getFile();
        byte[] body = null;
        InputStream is = new FileInputStream(resource);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + resource.getName());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }

    @GetMapping("/getTwo")
    public void getTwo(HttpServletRequest request, HttpServletResponse response) {
        try {
            File resource = resourceLoader.getResource("classpath:download/test.txt").getFile();
            download(resource.getName(), resource.getPath(), response);
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }

    }

    public static void download(String fileName, String filePath, HttpServletResponse response)
            throws Exception {
        //声明本次下载状态的记录对象
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        //用于记录以完成的下载的数据量，单位是byte
        try {
            //打开本地文件流
            InputStream inputStream = new FileInputStream(filePath);
            //激活下载操作
            OutputStream os = response.getOutputStream();

            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 方案三 : Response 实现下载
     */
    @GetMapping("get")
    public void getFile() {
        logger.info("------> this is in get <-------");
        try {
            HttpServletResponse response =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            File resource = resourceLoader.getResource("classpath:download/test.txt").getFile();
            download(resource.getName(), resource.getPath(), response);
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
    }

    public void outFile() throws Exception {

        HttpServletResponse httpServletResponse =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        File resource = resourceLoader.getResource("classpath:download/test.txt").getFile();
        httpServletResponse.setContentType("multipart/form-data");
        httpServletResponse.setHeader("Content-Disposition", "attchement;filename=" + resource.getName());

        byte[] file = getBytesByFile(resource);
        try {
            ServletOutputStream out = httpServletResponse.getOutputStream();
            if (file != null && file.length > 0) {
                int len = file.length;
                out.write(file, 0, len);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static byte[] getBytesByFile(File file) {
        try {
            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            //
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
