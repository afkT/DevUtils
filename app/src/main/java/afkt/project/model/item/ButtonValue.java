package afkt.project.model.item;

import java.util.ArrayList;
import java.util.List;

import dev.utils.app.ResourceUtils;

/**
 * detail: Button Value 实体类
 * @author Ttt
 */
public class ButtonValue {

    // 按钮类型
    public int type;
    // 文案
    public String text;

    public ButtonValue(int type, int id) {
        this(type, ResourceUtils.getString(id));
    }

    public ButtonValue(int type, String text) {
        this.type = type;
        this.text = text;
    }

    /**
     * 获取 Main Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getMainButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_WIFI, "Wifi 相关 ( 热点 )"));
        return lists;
    }

    /**
     * 获取 Wifi Button Value 集合
     * @return {@link List < ButtonValue >}
     */
    public static List<ButtonValue> getWifiButtonValues() {
        List<ButtonValue> lists = new ArrayList<>();
        lists.add(new ButtonValue(BTN_WIFI_OPEN, "打开 Wifi"));
        lists.add(new ButtonValue(BTN_WIFI_CLOSE, "关闭 Wifi"));
        lists.add(new ButtonValue(BTN_WIFI_HOT_OPEN, "打开 Wifi 热点"));
        lists.add(new ButtonValue(BTN_WIFI_HOT_CLOSE, "关闭 Wifi 热点"));
        lists.add(new ButtonValue(BTN_WIFI_LISTENER_REGISTER, "注册 Wifi 监听"));
        lists.add(new ButtonValue(BTN_WIFI_LISTENER_UNREGISTER, "注销 Wifi 监听"));
        return lists;
    }

    // ========
    // = 常量 =
    // ========

    private static final int BASE = 2000;
    // Wifi 相关 ( 热点 )
    public static final int BTN_WIFI = BASE;
    // 打开 Wifi
    public static final int BTN_WIFI_OPEN = BTN_WIFI + 1;
    // 关闭 Wifi
    public static final int BTN_WIFI_CLOSE = BTN_WIFI + 2;
    // 打开 Wifi 热点
    public static final int BTN_WIFI_HOT_OPEN = BTN_WIFI + 3;
    // 关闭 Wifi 热点
    public static final int BTN_WIFI_HOT_CLOSE = BTN_WIFI + 4;
    // 注册 Wifi 监听
    public static final int BTN_WIFI_LISTENER_REGISTER = BTN_WIFI + 5;
    // 注销 Wifi 监听
    public static final int BTN_WIFI_LISTENER_UNREGISTER = BTN_WIFI + 6;
}
