package afkt.project.base.constants;

/**
 * detail: Key 常量
 * @author Ttt
 */
public final class KeyConstants {

    private KeyConstants() {
    }

    /**
     * detail: 传参 Key
     * @author Ttt
     */
    public final class Extra {

        private Extra() {
        }

        // KEY 数据
        public static final String KEY_DATA   = Common.KEY_DATA;
        // 标题
        public static final String KEY_TITLE  = Common.KEY_TITLE;
        // 对象
        public static final String KEY_OBJECT = Common.KEY_OBJECT;
        // 参数
        public static final String KEY_PARAMS = Common.KEY_PARAMS;
    }

    /**
     * detail: 通用 Key
     * @author Ttt
     */
    public final class Common {

        private Common() {
        }

        // 数据
        public static final String KEY_DATA   = "data";
        // 标题
        public static final String KEY_TITLE  = "title";
        // 对象
        public static final String KEY_OBJECT = "object";
        // 参数
        public static final String KEY_PARAMS = "params";
        // 类型
        public static final String KEY_TYPE   = "type";
    }

    /**
     * detail: 其他 Key
     * @author Ttt
     */
    public final class Other {

        // 解码类型
        public static final String KEY_DECODEMODE = "decodeMode";
    }
}