package com.gang.study.utilsexample.utilsexample;

import com.gang.study.utilsexample.utilsexample.apachecommon.HttpClientExample;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest
class UtilsexampleApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpClientExample example;

    @Test
    void contextLoads() {
//        doGet();
        doPost();
    }

    public void doGet() {
        try {
            URI uri = new URIBuilder("http://infosec.zentaopm.com/bug-browse-30--unclosed-0--72-20-2.html").build();
            logger.info("------> back :{} <-------", example.doGet(uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void doPost() {


        try {
            URI uri = new URIBuilder("https://www.runoob.com/try/ajax/demo_test_post.php").build();
            logger.info("------> back :{} <-------", example.doPost(uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
