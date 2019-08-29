package dev.assist;

import dev.base.DevBaseVariable;

/**
 * detail: 变量辅助类
 * @author Ttt
 */
public class VariableAssist {

    // Boolean 变量存储对象
    private DevBaseVariable<Object, Boolean> booleanVariable = new DevBaseVariable<>();
    // Object 变量存储对象
    private DevBaseVariable<Object, Object> objectVariable = new DevBaseVariable<>();

    /**
     * 获取 Boolean 变量存储对象
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<Object, Boolean> getBooleanVariable() {
        return booleanVariable;
    }

    /**
     * 获取 Object 变量存储对象
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<Object, Object> getObjectVariable() {
        return objectVariable;
    }
}
