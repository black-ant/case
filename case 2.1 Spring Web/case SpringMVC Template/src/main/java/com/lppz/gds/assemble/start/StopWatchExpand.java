package om.lppz.gds.assemble.start;


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
            // 记录当前时间戳
            long currentTimeMillis = System.currentTimeMillis();

            // 将流程线、节点行为和时间戳保存到集合
            taskEntries.add(new TaskEntry(processLine, node, currentTimeMillis));

            return "[ 流程: " + processLine + " | 节点: " + node + " ] 监测到达时间: " + formatTimestamp(currentTimeMillis);
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
    }

    /**
     * 打印所有任务的时间节点以及间隔时间和百分比
     */
    private static void logStatistics() {
        // 1. 预处理：计算最大节点行为长度和最大流程行长度
        int maxNodeLength = taskEntries.stream()
                .map(entry -> entry.getNode().length())
                .max(Integer::compare)
                .orElse(30); // 默认最小宽度为18

        // 保证processLine长度为30
        int processLineWidth = 10;

        // 2. 创建格式化模板，使用动态宽度
        String headerFormat = "%-" + (maxNodeLength+10) + "s |%-" + processLineWidth + "s | %-9s | %-12s | %-24s | %-30s\n";
        String rowFormat = "%-" + processLineWidth + "s |%-11d ms | %-14.2f%% | %-31d ms | %-30d ms\n";

        // 3. 构建日志字符串
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------------------------------------------------------\n");
        sb.append("时间节点统计:\n");
        sb.append("--------------------------------------------------------------------------------------------------\n");

        // 4. 使用动态宽度的格式化模板打印表头
        sb.append(String.format(headerFormat,
                "节点行为", "流程", "节点耗时", "占比", "相对总初始节点时间戳", "相对当前流程初始节点时间戳"));
        sb.append("--------------------------------------------------------------------------------------------------\n");

        // 5. 遍历并格式化每一行
        for (int i = 0; i < taskEntries.size(); i++) {
            TaskEntry entry = taskEntries.get(i);

            // 填充processLine到固定长度
            String processLine = String.format("%-" + processLineWidth + "s",
                    entry.getProcessLine().length() > processLineWidth
                            ? entry.getProcessLine().substring(0, processLineWidth)
                            : entry.getProcessLine());

            String node = padChineseNode(entry.getNode(), maxNodeLength+10);
            long taskTimeMillis = entry.getTimeMillis();

            // 6. 计算时间相关指标
            long interval = (i == 0) ? 0 : (taskTimeMillis - taskEntries.get(i - 1).getTimeMillis());
            double totalDuration = (i == 0) ? 1 : (taskEntries.get(i).getTimeMillis() - taskEntries.get(0).getTimeMillis());
            double intervalPercentage = (totalDuration > 0) ? (double) interval / totalDuration * 100 : 0.0;

            long relativeToInitial = taskTimeMillis - taskEntries.get(0).getTimeMillis();
            long relativeToCurrentProcess = (i == 0) ? 0 : (taskTimeMillis - taskEntries.get(0).getTimeMillis());

            // 7. 使用动态宽度的格式化模板打印每一行
            sb.append("| ");
            sb.append(node);
            sb.append("   |");
            sb.append(String.format(rowFormat,
                    processLine,
                    interval,
                    intervalPercentage,
                    relativeToInitial,
                    relativeToCurrentProcess));
        }

        sb.append("---------------------------------------------------------------\n");

        // 8. 输出到日志
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

    public static void main(String[] args) {
        int maxNodeLength = 30;
        String originalNode = "Listener获取完成开始启动";
        String paddedNode = padChineseNode(originalNode, maxNodeLength);
        System.out.println(paddedNode);

        String originalNode2 = "开始处理环境";
        String paddedNode2 = padChineseNode(originalNode2, maxNodeLength);
        System.out.println(paddedNode2);
    }

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
     * 任务条目类，用于存储流程、节点行为和时间戳
     */
    private static class TaskEntry {
        private String processLine; // 流程线
        private String node;         // 节点行为
        private long timeMillis;     // 时间戳

        public TaskEntry(String processLine, String node, long timeMillis) {
            this.processLine = processLine;
            this.node = node;
            this.timeMillis = timeMillis;
        }

        public String getProcessLine() {
            return processLine;
        }

        public String getNode() {
            return node;
        }

        public long getTimeMillis() {
            return timeMillis;
        }
    }
}