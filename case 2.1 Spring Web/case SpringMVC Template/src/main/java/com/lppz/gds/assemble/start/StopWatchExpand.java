package com.lppz.gds.assemble.start;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 检测程序片段运行时间拓展
 */
public class StopWatchExpand {

    /**
     * 存储时间节点的静态集合
     */
    private static List<TaskEntry> taskEntries = new ArrayList<>();

    /**
     * 开启计时
     *
     * @param processLine 流程线
     * @param node        节点行为
     * @return 提示字符串
     */
    public static String start(String processLine, String node) {
        try {
            // 获取调用的类和方法信息
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            // 注意: stackTrace[2]是调用start方法的方法
            String callerClass = stackTrace[2].getClassName().substring(
                    stackTrace[2].getClassName().lastIndexOf(".") + 1
            );
            String callerMethod = stackTrace[2].getMethodName();

            // 记录当前时间戳
            long currentTimeMillis = System.currentTimeMillis();

            // 将流程线、节点行为、调用类、调用方法和时间戳保存到集合
            taskEntries.add(new TaskEntry(processLine, node, callerClass, callerMethod, currentTimeMillis));

            return String.format("[ 流程: %s | 节点: %s | 调用类: %s | 调用方法: %s ] 监测到达时间: %s",
                    processLine, node, callerClass, callerMethod, formatTimestamp(currentTimeMillis));
        } catch (Exception e) {
            e.printStackTrace();
            return "执行异常: " + node;
        }
    }

    /**
     * 结束计时并记录统计
     */
    public static void stop() {
        logStatistics();
        // 清空记录，防止跨调用干扰
        taskEntries.clear();
    }

    /**
     * 打印所有任务的时间节点以及间隔时间和百分比
     */
    private static void logStatistics() {
        if (taskEntries.isEmpty()) {
            return;
        }

        // 1. 预处理：计算最大节点行为长度和最大流程行长度
        int maxNodeLength = taskEntries.stream()
                .map(entry -> entry.getNode().length())
                .max(Integer::compare)
                .orElse(30); // 默认最小宽度为30

        // 保证processLine长度为10
        int processLineWidth = 10;
        int classNameWidth = 30;
        int methodNameWidth = 30;

        // 2. 创建格式化模板，使用动态宽度
        String headerFormat = "%-" + (maxNodeLength + 10) + "s |%-" + processLineWidth + "s | %-" + classNameWidth + "s | %-" + methodNameWidth + "s | %-9s | %-12s | %-24s | %-30s\n";
        String rowFormat = "%-" + processLineWidth + "s |%-" + (classNameWidth+4) + "s | %-" + (methodNameWidth+4) + "s |%-11d ms | %-13.2f%% | %-31d ms | %-30d ms\n";

        // 3. 计算总时长：从第一个节点到最后一个节点的时间差
        long startTime = taskEntries.get(0).getTimeMillis();
        long endTime = taskEntries.get(taskEntries.size() - 1).getTimeMillis();
        long totalDuration = endTime - startTime;

        // 4. 构建日志字符串
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------------------------------------------------------\n");
        sb.append("时间节点统计:\n");
        sb.append("--------------------------------------------------------------------------------------------------\n");

        // 5. 使用动态宽度的格式化模板打印表头
        sb.append(String.format(headerFormat,
                "节点行为", "流程", "调用类", "调用方法", "节点耗时", "占比", "相对总初始节点时间戳",
                "相对当前流程初始节点时间戳"));
        sb.append("--------------------------------------------------------------------------------------------------\n");

        // 6. 遍历并格式化每一行
        for (int i = 0; i < taskEntries.size(); i++) {
            TaskEntry entry = taskEntries.get(i);

            // 填充processLine到固定长度
            String processLine = String.format("%-" + processLineWidth + "s",
                    entry.getProcessLine().length() > processLineWidth
                            ? entry.getProcessLine().substring(0, processLineWidth)
                            : entry.getProcessLine());

            // 截断或填充调用类和方法名
            String callerClass = padString(entry.getCallerClass(), classNameWidth);
            String callerMethod = padString(entry.getCallerMethod(), methodNameWidth);

            String node = padChineseNode(entry.getNode(), maxNodeLength + 10);
            long taskTimeMillis = entry.getTimeMillis();

            // 7. 计算时间相关指标
            long interval = (i == 0) ? 0 : (taskTimeMillis - taskEntries.get(i - 1).getTimeMillis());
            double intervalPercentage = totalDuration > 0 ? ((double) interval / totalDuration) * 100 : 0.0;

            long relativeToInitial = taskTimeMillis - startTime;
            long relativeToCurrentProcess = (i == 0) ? 0 : (taskTimeMillis - startTime);

            // 8. 使用动态宽度的格式化模板打印每一行
            sb.append("| ");
            sb.append(node);
            sb.append("   |");
            sb.append(String.format(rowFormat,
                    processLine,
                    callerClass,
                    callerMethod,
                    interval,
                    intervalPercentage,
                    relativeToInitial,
                    relativeToCurrentProcess));
        }

        sb.append("---------------------------------------------------------------\n");

        // 9. 输出到日志
        System.out.println(sb);
    }

    /**
     * 格式化时间戳为字符串
     *
     * @param timestamp 时间戳
     * @return 格式化后的字符串
     */
    private static String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(timestamp);
    }

    /**
     * 填充或截断字符串到指定长度
     */
    private static String padString(String input, int length) {
        if (input == null) input = "";
        if (input.length() > length) {
            return input.substring(0, length);
        }
        return String.format("%-" + length + "s", input);
    }

    /**
     * 处理中文节点的填充
     */
    private static String padChineseNode(String node, int maxNodeLength) {
        int width = 0;
        StringBuilder result = new StringBuilder();

        for (char c : node.toCharArray()) {
            int charWidth = (c >= 0x4E00 && c <= 0x9FA5) ||
                    (c >= 'a' && c <= 'z') ||
                    (c >= 'A' && c <= 'Z') ?
                    ((c >= 0x4E00 && c <= 0x9FA5) ? 2 : 1) : 0;

            if (width + charWidth > maxNodeLength) {
                break;
            }

            if (charWidth > 0) {
                result.append(c);
                width += charWidth;
            }
        }

        while (width < maxNodeLength) {
            result.append(" ");
            width++;
        }

        return result.toString();
    }

    /**
     * 任务条目类，增加调用类和调用方法字段
     */
    private static class TaskEntry {
        private String processLine;   // 流程线
        private String node;           // 节点行为
        private String callerClass;    // 调用类
        private String callerMethod;   // 调用方法
        private long timeMillis;       // 时间戳

        public TaskEntry(String processLine, String node, String callerClass, String callerMethod, long timeMillis) {
            this.processLine = processLine;
            this.node = node;
            this.callerClass = callerClass;
            this.callerMethod = callerMethod;
            this.timeMillis = timeMillis;
        }

        // 增加getter方法
        public String getProcessLine() { return processLine; }
        public String getNode() { return node; }
        public String getCallerClass() { return callerClass; }
        public String getCallerMethod() { return callerMethod; }
        public long getTimeMillis() { return timeMillis; }
    }

    // 可选的main方法，用于测试
    public static void main(String[] args) {
        // 测试用例
        start("测试流程", "开始处理");
        try {
            Thread.sleep(100); // 模拟处理时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        start("测试流程", "处理中");
        try {
            Thread.sleep(50); // 模拟处理时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop();
    }
}