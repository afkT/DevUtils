package dev.capture;

import java.util.List;

import dev.utils.common.ConvertUtils;
import dev.utils.common.StringUtils;

class Items {

    /**
     * 首页适配器数据包装类
     */
    public static class MainItem {
        public String            moduleName;
        public List<CaptureItem> lists;

        public MainItem(
                final String moduleName,
                final List<CaptureItem> lists
        ) {
            this.moduleName = moduleName;
            this.lists      = lists;
        }
    }

    /**
     * 抓包列表数据包装类
     */
    public static class GroupItem {
        public String            title;
        public List<CaptureFile> lists;

        public GroupItem(
                final String title,
                final List<CaptureFile> lists
        ) {
            this.title = convertTitleByHHMM(title);
            this.lists = lists;
        }
    }

    /**
     * 抓包文件数据包装类
     */
    public static class FileItem {
        public String title;
        public String value;

        public FileItem(
                final String title,
                final String value
        ) {
            this.title = title;
            this.value = value;
        }
    }

    /**
     * 通过时间转换标题
     * <pre>
     *     例
     *     1000 转换为 10 hour 00-14 minute
     *     1015 转换为 10 hour 15-29 minute
     *     1030 转换为 10 hour 30-44 minute
     *     1045 转换为 10 hour 45-59 minute
     * </pre>
     * @param title HHMM
     * @return 转换后的标题
     */
    protected static String convertTitleByHHMM(final String title) {
        if (StringUtils.isLength(title, 4)) {
            StringBuilder builder = new StringBuilder();
            builder.append(title, 0, 2).append(" hour ");
            // 获取分钟
            String minute = StringUtils.substring(title, 2, 4, false);
            int    mm     = ConvertUtils.toInt(minute);
            // 存储间隔以 15 分钟为单位
            if (mm < 15) {
                minute = "00-14"; // 00-14
            } else if (mm < 30) {
                minute = "15-29"; // 15-29
            } else if (mm < 45) {
                minute = "30-44"; // 30-44
            } else {
                minute = "45-59"; // 45-59
            }
            builder.append(minute).append(" minute");
            return builder.toString();
        }
        return title;
    }
}