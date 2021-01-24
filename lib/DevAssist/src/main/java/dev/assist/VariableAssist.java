package dev.assist;

import dev.base.DevVariable;

/**
 * detail: 变量辅助类
 * @author Ttt
 */
public class VariableAssist {

    // Boolean 变量存储对象
    private final DevVariable<Object, Boolean> mBooleanVariable = new DevVariable<>();
    // Object 变量存储对象
    private final DevVariable<Object, Object>  mObjectVariable  = new DevVariable<>();

    /**
     * 获取 Boolean 变量存储对象
     * @return {@link DevVariable}
     */
    public DevVariable<Object, Boolean> getBooleanVariable() {
        return mBooleanVariable;
    }

    /**
     * 获取 Object 变量存储对象
     * @return {@link DevVariable}
     */
    public DevVariable<Object, Object> getObjectVariable() {
        return mObjectVariable;
    }
}