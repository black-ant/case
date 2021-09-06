package com.gang.algorithm.demo.search;

import com.alibaba.fastjson.JSONObject;
import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.TreeNode;
import com.gang.algorithm.demo.utils.TreeNodeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Classname Num_113_PathSumII
 * @Description TODO
 * @Date 2021/8/17
 * @Created by zengzg
 */
public class Num_113_PathSumII extends AbstractAlgorithmService {

    public static void main(String[] args) {
        new Num_113_PathSumII().run();
    }


    @Override
    public void run() {
        TreeNode root = TreeNodeUtils.buildListNode(new Object[]{5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, null, 5, 1});
        logger.info("------> [{}] <-------", JSONObject.toJSONString(pathSum(root, 22)));
    }

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        Stack<TreeNode> toVisit = new Stack<>();
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        TreeNode cur = root;
        TreeNode pre = null;
        int curSum = 0; // 记录当前的累计的和
        while (cur != null || !toVisit.isEmpty()) {
            while (cur != null) {
                toVisit.push(cur); // 添加根节点
                curSum += (Integer) cur.getVal();
                /************修改的地方******************/
                temp.add((Integer) cur.getVal());
                /**************************************/
                cur = cur.getLeft(); // 递归添加左节点
            }
            cur = toVisit.peek(); // 已经访问到最左的节点了
            // 判断是否满足条件
            if (curSum == sum && cur.getLeft() == null && cur.getRight() == null) {
                /************修改的地方******************/
                ans.add(new ArrayList<>(temp));
                /**************************************/
            }
            // 在不存在右节点或者右节点已经访问过的情况下，访问根节点
            if (cur.getRight() == null || cur.getLeft() == pre) {
                TreeNode pop = toVisit.pop();
                curSum -= pop.getVal(); // 减去出栈的值
                /************修改的地方******************/
                temp.remove(temp.size() - 1);
                /**************************************/
                pre = cur;
                cur = null;
            } else {
                cur = cur.getRight(); // 右节点还没有访问过就先访问右节点
            }
        }
        return ans;
    }

}
