package dev.capture;

import java.util.List;

class Items {

    public static final int TYPE_TITLE = 1;
    public static final int TYPE_ITEM  = 2;

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
}