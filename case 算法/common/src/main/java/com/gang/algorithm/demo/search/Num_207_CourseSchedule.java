package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @Classname Num_207_CourseSchedule
 * @Description TODO
 * @Date 2021/8/18
 * @Created by zengzg
 */
public class Num_207_CourseSchedule extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_115_DistinctSubsequences().run();
    }

    @Override
    public void run() {
        int[][] grid = {
                {1, 0},
                {0, 1}
        };
        canFinish(2, grid);
    }


    public boolean canFinish(int numCourses, int[][] prerequisites) {
        HashMap<Integer, ArrayList<Integer>> outNodes = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();
        int rows = prerequisites.length;
        for (int i = 0; i < rows; i++) {
            int key = prerequisites[i][0];
            int value = prerequisites[i][1];
            set.add(key);
            if (!outNodes.containsKey(key)) {
                outNodes.put(key, new ArrayList<>());
            }
            //存储当前节点的所有先修课程
            ArrayList<Integer> list = outNodes.get(key);
            list.add(value);
        }

        HashSet<Integer> visitedFinish = new HashSet<>();
        //判断每一门课
        for (int k : set) {
            if (!dfs(k, outNodes, new HashSet<>(), visitedFinish)) {
                return false;
            }
            visitedFinish.add(k);
        }
        return true;
    }

    private boolean dfs(int start, HashMap<Integer, ArrayList<Integer>> outNodes, HashSet<Integer> visited,
                        HashSet<Integer> visitedFinish) {
        //已经处理过或者到了叶子节点
        if (visitedFinish.contains(start) || !outNodes.containsKey(start)) {
            return true;
        }
        //出现了环
        if (visited.contains(start)) {
            return false;
        }
        //将当前节点加入路径
        visited.add(start);
        ArrayList<Integer> list = outNodes.get(start);
        for (int k : list) {
            if (!dfs(k, outNodes, visited, visitedFinish)) {
                return false;
            }
        }
        visited.remove(start);
        return true;
    }
}
