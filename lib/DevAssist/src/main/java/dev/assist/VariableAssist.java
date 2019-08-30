package dev.assist;

import dev.base.DevBaseVariable;

/**
 * detail: 变量辅助类
 * @author Ttt
 */
public class VariableAssist {

    // Boolean 变量存储对象
    private DevBaseVariable<Object, Boolean> mBooleanVariable = new DevBaseVariable<>();
    // Object 变量存储对象
    private DevBaseVariable<Object, Object> mObjectVariable = new DevBaseVariable<>();

    /**
     * 获取 Boolean 变量存储对象
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<Object, Boolean> getBooleanVariable() {
        return mBooleanVariable;
    }

    /**
     * 获取 Object 变量存储对象
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<Object, Object> getObjectVariable() {
        return mObjectVariable;
    }
}
