package com.gang.algorithm.demo.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Classname ArraysUtils
 * @Description TODO
 * @Date 2021/8/17
 * @Created by zengzg
 */
public class ArraysUtils {

    /**
     * 双层数组转 List
     *
     * @return
     */
    public static <E> List<List<E>> arraysToList(int[][] arrays) {

        List<List<E>> list = new ArrayList<>();
        for (int i = 0; i < arrays.length; i++) {
            List listChild = list.get(i) == null ? new ArrayList() : list.get(i);
            for (int j = 0; j < arrays[i].length; j++) {
                listChild.add(arrays[i][j]);
            }
        }
        return list;
    }
}
