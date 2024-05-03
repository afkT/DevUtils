package dev.environment;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.environment.bean.EnvironmentBean;
import dev.environment.bean.ModuleBean;
import dev.environment.log.LogUtils;

/**
 * detail: 环境配置 Item
 * @author Ttt
 */
public final class AdapterItem {

    private static final int MODULE_TYPE      = 1;
    private static final int ENVIRONMENT_TYPE = 2;

    // Item 类型
    private final int             itemType;
    // 判断 Environment 是否选中
    private final int             hashCodeEquals;
    // Module 实体类 ( Module 类型使用 )
    public final  ModuleBean      moduleBean;
    // Environment 实体类 ( Environment 类型使用 )
    public final  EnvironmentBean environmentBean;

    AdapterItem(final ModuleBean moduleBean) {
        this.itemType        = MODULE_TYPE;
        this.hashCodeEquals  = -1;
        this.moduleBean      = moduleBean;
        this.environmentBean = null;
    }

    AdapterItem(final EnvironmentBean environmentBean) {
        this.itemType        = ENVIRONMENT_TYPE;
        this.hashCodeEquals  = environmentBean.hashCode();
        this.moduleBean      = null;
        this.environmentBean = environmentBean;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否 Module 类型
     * @return {@code true} yes, {@code false} no
     */
    public boolean isModule() {
        return itemType == MODULE_TYPE;
    }

    /**
     * 是否 Environment 类型
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEnvironment() {
        return itemType == ENVIRONMENT_TYPE;
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

    /**
     * 设置 Module Selected Environment
     * @param context {@link Context}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setModuleEnvironment(final Context context) {
        if (isEnvironment()) {
            return DevEnvironmentUtils.setModuleEnvironment(context, environmentBean);
        }
        return false;
    }

    // =

    /**
     * 获取模块、环境名
     * @return 模块、环境名
     */
    public String getName() {
        if (isModule()) return moduleBean.getName();
        if (isEnvironment()) return environmentBean.getName();
        return null;
    }

    /**
     * 获取模块、环境别名
     * @return 模块、环境别名
     */
    public String getAlias() {
        if (isModule()) return moduleBean.getAlias();
        if (isEnvironment()) return environmentBean.getAlias();
        return null;
    }

    /**
     * 获取显示名
     * @return 显示名
     */
    public String getDisplayName() {
        String name  = getName();
        String alias = getAlias();
        return TextUtils.isEmpty(alias) ? name : alias;
    }

    /**
     * 获取环境配置值
     * @return 环境配置值
     */
    public String getValue() {
        if (isEnvironment()) return environmentBean.getValue();
        return null;
    }

    // ==========
    // = 静态方法 =
    // ==========

    /**
     * 获取环境配置 Item List
     * @param context {@link Context}
     * @return AdapterItem List
     */
    static List<AdapterItem> getAdapterItems(final Context context) {
        refreshHashCode(context);
        return _getAdapterItems(context);
    }

    // ==========
    // = 内部方法 =
    // ==========

    // 各个 Module 选中的 Environment HashCode
    private static final HashMap<String, Integer> sModuleHashCodeMap = new HashMap<>();

    /**
     * 获取环境配置 Item List
     * @param context {@link Context}
     * @return AdapterItem List
     */
    private static List<AdapterItem> _getAdapterItems(final Context context) {
        List<AdapterItem> items   = new ArrayList<>();
        List<ModuleBean>  modules = DevEnvironmentUtils.getModuleList();
        if (modules != null) {
            for (ModuleBean moduleBean : modules) {
                if (moduleBean != null) {
                    List<EnvironmentBean> environments = moduleBean.getEnvironments();
                    if (environments != null && !environments.isEmpty()) {
                        // 添加 Module Type
                        items.add(new AdapterItem(moduleBean));
                        // 判断是否添加自定义配置
                        boolean addCustom = true;
                        for (EnvironmentBean environmentBean : environments) {
                            if (environmentBean != null) {
                                // 添加 Environment Type
                                AdapterItem adapterItem = new AdapterItem(environmentBean);
                                items.add(adapterItem);
                                if (adapterItem.isSelect()) {
                                    addCustom = false;
                                }
                            }
                        }
                        if (addCustom) {
                            // 获取选中的环境
                            EnvironmentBean environmentSelect = DevEnvironmentUtils.getModuleEnvironment(
                                    context, moduleBean.getName()
                            );
                            if (environmentSelect != null) {
                                // 添加 Environment Type
                                items.add(new AdapterItem(environmentSelect));
                            }
                        }
                    }
                }
            }
        }
        return items;
    }

    /**
     * 刷新 HashCode
     * @param context {@link Context}
     */
    private static void refreshHashCode(final Context context) {
        List<ModuleBean> modules = DevEnvironmentUtils.getModuleList();
        if (modules != null) {
            for (ModuleBean moduleBean : modules) {
                if (moduleBean != null) {
                    // 获取选中的环境
                    EnvironmentBean environmentSelect = DevEnvironmentUtils.getModuleEnvironment(
                            context, moduleBean.getName()
                    );
                    if (environmentSelect != null) {
                        sModuleHashCodeMap.put(moduleBean.getName(), environmentSelect.hashCode());
                    }
                }
            }
        }
    }

    /**
     * 改变 HashCode
     * @param newEnvironment environment bean
     */
    static void changeHashCode(final EnvironmentBean newEnvironment) {
        try {
            sModuleHashCodeMap.put(newEnvironment.getModule().getName(), newEnvironment.hashCode());
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }
}