package com.gang.algorithm.demo.service;

import com.gang.algorithm.demo.search.Num_67_AddBinary;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/8/8
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {

        search();
    }

    public void search() {

        // 给定一个字符串，找到没有重复字符的最长子串，返回它的长度
//        new FindDuplicateStrings().findDuplicateStrings.run();

        // 给定一个数组，每个数字只允许出现 2 次，将满足条件的数字全部移到前边，并且返回有多少数字
//        new Num_88_RemoveDuplicatesFromSortedArrayII().run();

        // 在图中索引单词
//        new Num_79_WordSearch().run();

        // 判断一个矩阵中是否存在某个数，矩阵是有序的
//        new Num_74_Search2DMatrix().run();

//        new Num_71_SimplifyPath().run();

        new Num_67_AddBinary().run();
    }
}
