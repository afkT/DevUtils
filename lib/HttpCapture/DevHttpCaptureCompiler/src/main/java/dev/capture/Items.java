package dev.capture;

import java.util.List;

class Items {

    /**
     * 首页适配器数据包装类
     */
    public static class MainItem {
        public String            moduleName;
        public List<CaptureItem> lists;

        public MainItem(
                String moduleName,
                List<CaptureItem> lists
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
                String title,
                List<CaptureFile> lists
        ) {
            this.title = title;
            this.lists = lists;
        }
    }
}