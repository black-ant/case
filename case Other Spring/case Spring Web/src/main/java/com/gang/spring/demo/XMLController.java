package com.gang.spring.demo;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @Classname XMLController
 * @Description TODO
 * @Date 2020/11/4 15:45
 * @Created by zengzg
 */
@RestController
@RequestMapping("xmlTest")
public class XMLController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CONTENT_TYPE = "text/xml;charset=UTF-8";

    @GetMapping(path = "get")
    public void generateMetadataForIdp(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final File metadataFile = new File("D:\\file\\pom.xml");
        final String contents = FileUtils.readFileToString(metadataFile, StandardCharsets.UTF_8);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter writer = response.getWriter()) {
            logger.debug("Producing metadata for the response");
            writer.write(contents);
            writer.flush();
        }
    }
}
