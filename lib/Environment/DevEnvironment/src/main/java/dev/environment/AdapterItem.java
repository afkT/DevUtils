package dev.environment;

import dev.environment.bean.EnvironmentBean;
import dev.environment.bean.ModuleBean;

/**
 * detail: 适配器 Item
 * @author Ttt
 */
class AdapterItem {

    public static final int MODULE_TYPE = 1;
    public static final int ENVIRONMENT_TYPE = 2;

    // Item 类型
    public final int itemType;
    // 判断 Environment 是否选中
    private final int hashCodeEquals;
    // Module 实体类 ( Module 类型使用 )
    public ModuleBean moduleBean;
    // Environment 实体类 ( Environment 类型使用 )
    public EnvironmentBean environmentBean;

    public AdapterItem(ModuleBean moduleBean) {
        this.itemType = MODULE_TYPE;
        this.hashCodeEquals = -1;
        this.moduleBean = moduleBean;
    }

    public AdapterItem(EnvironmentBean environmentBean) {
        this.itemType = ENVIRONMENT_TYPE;
        this.hashCodeEquals = environmentBean.hashCode();
        this.environmentBean = environmentBean;
    }
}