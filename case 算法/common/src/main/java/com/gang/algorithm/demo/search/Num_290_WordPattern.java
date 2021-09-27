package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @Classname Num_290_WordPattern
 * @Description TODO
 * @Date 2021/9/11
 * @Created by zengzg
 */
public class Num_290_WordPattern extends AbstractAlgorithmService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        new Num_290_WordPattern().run();
    }

    @Override
    public void run() {
        Boolean isTrue = wordPattern("abba", "dog cat cat dog");
        logger.info("------> word :{} <-------", isTrue);
    }

    public boolean wordPattern(String pattern, String str) {
        String[] array1 = str.split(" ");
        if (array1.length != pattern.length()) {
            return false;
        }
        String[] array2 = pattern.split("");
        //两个方向的映射
        return wordPatternHelper(array1, array2) && wordPatternHelper(array2, array1);
    }

    //array1 到 array2 的映射
    private boolean wordPatternHelper(String[] array1, String[] array2) {
        // 核心就是 key value
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < array1.length; i++) {
            String key = array1[i];
            if (map.containsKey(key)) {
                if (!map.get(key).equals(array2[i])) {
                    return false;
                }
            } else {
                map.put(key, array2[i]);
            }
        }
        return true;
    }
}
