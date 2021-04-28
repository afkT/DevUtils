package afkt.project.base.constants

/**
 * detail: Key 常量
 * @author Ttt
 */
class KeyConstants private constructor() {

    /**
     * detail: 传参 Key
     * @author Ttt
     */
    object Extra {
        // KEY 数据
        const val KEY_DATA = Common.KEY_DATA

        // 标题
        const val KEY_TITLE = Common.KEY_TITLE

        // 对象
        const val KEY_OBJECT = Common.KEY_OBJECT

        // 参数
        const val KEY_PARAMS = Common.KEY_PARAMS
    }

    /**
     * detail: 通用 Key
     * @author Ttt
     */
    object Common {
        // 数据
        const val KEY_DATA = "data"

        // 标题
        const val KEY_TITLE = "title"

        // 对象
        const val KEY_OBJECT = "object"

        // 参数
        const val KEY_PARAMS = "params"

        // 类型
        const val KEY_TYPE = "type"
    }

    /**
     * detail: 其他 Key
     * @author Ttt
     */
    object Other {
        // 解码类型
        const val KEY_DECODEMODE = "decodeMode"
    }
}