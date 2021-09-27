package com.gang.algorithm.demo.search;

import com.gang.algorithm.demo.service.AbstractAlgorithmService;
import com.gang.algorithm.demo.to.TreeNode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @Classname Num_297_SerializeAndDeserializeBinaryTree
 * @Description TODO
 * @Date 2021/9/11
 * @Created by zengzg
 */
public class Num_297_SerializeAndDeserializeBinaryTree extends AbstractAlgorithmService {

    private static final String spliter = ",";
    private static final String NN = "X"; //当做 null

    public static void main(String[] args) {
        new Num_297_SerializeAndDeserializeBinaryTree().run();
    }

    @Override
    public void run() {
        TreeNode treeNode = deserialize("1,2,3,null,null,4,5");
        String data = serialize(treeNode);

        logger.info("------> data is :{} <-------", data);
    }


    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        buildString(root, sb);
        return sb.toString();
    }

    private void buildString(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(NN).append(spliter);
        } else {
            sb.append(node.val).append(spliter);
            buildString(node.left, sb);
            buildString(node.right, sb);
        }
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Deque<String> nodes = new LinkedList<>();
        nodes.addAll(Arrays.asList(data.split(spliter)));
        return buildTree(nodes);
    }

    private TreeNode buildTree(Deque<String> nodes) {
        String val = nodes.remove();
        if (val.equals(NN)) {
            return null;
        } else if (val.equals("null")) {
            return null;
        } else {
            TreeNode node = new TreeNode(Integer.valueOf(val));
            node.left = buildTree(nodes);
            node.right = buildTree(nodes);
            return node;
        }
    }

}
