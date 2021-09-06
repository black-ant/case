package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

/**
 * @Classname Num_205_IsomorphicStrings
 * @Description TODO
 * @Date 2021/8/18
 * @Created by zengzg
 */
public class Num_205_IsomorphicStrings extends AbstractAlgorithmService {


    public static void main(String[] args) {
        new Num_205_IsomorphicStrings().run();
    }

    /**
     * 判断字符串模式是不是相同
     */
    @Override
    public void run() {
        logger.info("------> [{}] <-------", isIsomorphic("abb", "cad"));
    }

    public boolean isIsomorphic(String s, String t) {
        int n = s.length();
        int[] mapS = new int[128];
        int[] mapT = new int[128];
        for (int i = 0; i < n; i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            //当前的映射值是否相同
            if (mapS[c1] != mapT[c2]) {
                return false;
            } else {
                //是否已经修改过，修改过就不需要再处理
                if (mapS[c1] == 0) {
                    mapS[c1] = i + 1;
                    mapT[c2] = i + 1;
                }
            }
        }
        return true;
    }

}
