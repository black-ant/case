package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname Num_71_SimplifyPath
 * @Description TODO
 * @Date 2021/8/9
 * @Created by zengzg
 */
public class Num_71_SimplifyPath extends AbstractAlgorithmService {

    @Override
    public void run() {
//        logger.info("------> /home/ => {} <-------", simplifyPath("/home/"));
//        logger.info("------> /../ => {} <-------", simplifyPath("/../"));
//        logger.info("------> /home//test/ => {} <-------", simplifyPath("/home//test/"));
        logger.info("------> /a/./b/../../c/ => {} <-------", simplifyPath("/a/./b/../../c/"));
    }


    public String simplifyPath(String path) {
        char[] chars = path.toCharArray();
        StringBuffer buffer = new StringBuffer();

        int fast = 0;
        int slow = 0;
        int index = 0;
        for (; fast < chars.length; fast++) {
            if (chars[fast] == '/' && fast == slow) {
                if (fast != chars.length - 1) {
                    buffer.append(chars[slow]);
                }
            } else {
                if (chars[fast] == '.' || chars[fast] == '/') {
                    continue;
                }
                buffer.append(chars[fast]);
                slow = fast + 1;
            }
        }
        return buffer.toString();
    }
}
