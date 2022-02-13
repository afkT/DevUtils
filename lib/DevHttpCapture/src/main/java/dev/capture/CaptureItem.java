package dev.capture;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * detail: 抓包存储 Item
 * @author Ttt
 */
public class CaptureItem {

    // 存储时间 - 年月日 ( 文件夹名 )
    private String yyyyMMdd;

    // 存储数据 - 时分
    private final Map<String, List<CaptureFile>> data = new LinkedHashMap<>();

    // =======
    // = get =
    // =======

    public String getYyyyMMdd() {
        return yyyyMMdd;
    }

    public Map<String, List<CaptureFile>> getData() {
        return data;
    }

    // =======
    // = set =
    // =======

    CaptureItem setYyyyMMdd(String yyyyMMdd) {
        this.yyyyMMdd = yyyyMMdd;
        return this;
    }
}