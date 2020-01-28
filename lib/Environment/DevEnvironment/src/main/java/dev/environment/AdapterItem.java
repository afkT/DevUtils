package dev.environment;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    /**
     * 是否选中当前环境
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSelect() {
        if (environmentBean != null) {
            ModuleBean moduleBean = environmentBean.getModule();
            if (moduleBean != null) {
                Integer hashCode = sModuleHashCodeMap.get(moduleBean.getName());
                return (hashCode != null && hashCode == hashCodeEquals);
            }
        }
        return false;
    }

    // =

    // 各个 Module 选中的 Environment HashCode
    private static HashMap<String, Integer> sModuleHashCodeMap = new HashMap<>();

    /**
     * 刷新 HashCode
     * @param context {@link Context}
     */
    public static void refreshHashCode(final Context context) {
        List<ModuleBean> modules = Utils.getModuleList();
        if (modules != null) {
            for (ModuleBean moduleBean : modules) {
                if (moduleBean != null) {
                    String key = moduleBean.getName();
                    EnvironmentBean environmentBean = Utils.getModuleEnvironment(context, key);
                    if (environmentBean != null) {
                        sModuleHashCodeMap.put(moduleBean.getName(), environmentBean.hashCode());
                    }
                }
            }
        }
    }

    /**
     * 获取 Item List
     * @return AdapterItem List
     */
    public static List<AdapterItem> getAdapterItems() {
        List<AdapterItem> items = new ArrayList<>();
        List<ModuleBean> modules = Utils.getModuleList();
        if (modules != null) {
            for (ModuleBean moduleBean : modules) {
                if (moduleBean != null) {
                    List<EnvironmentBean> environments = moduleBean.getEnvironments();
                    if (environments != null && environments.size() != 0) {
                        // 添加 Module Type
                        items.add(new AdapterItem(moduleBean));
                        for (EnvironmentBean environmentBean : environments) {
                            if (environmentBean != null) {
                                // 添加 Environment Type
                                items.add(new AdapterItem(environmentBean));
                            }
                        }
                    }
                }
            }
        }
        return items;
    }
}