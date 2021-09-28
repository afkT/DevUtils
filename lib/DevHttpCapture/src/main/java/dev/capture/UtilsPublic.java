package dev.capture;

import java.util.List;
import java.util.Map;

public class UtilsPublic {

    private UtilsPublic() {
    }

    /**
     * 获取全部模块所有抓包数据
     * @param isEncrypt 是否加密数据
     * @return 全部模块所有抓包数据
     */
    public static Map<String, List<CaptureItem>> getAllModule(
            final boolean isEncrypt
    ) {
        return Utils.getAllModule(isEncrypt);
    }
}