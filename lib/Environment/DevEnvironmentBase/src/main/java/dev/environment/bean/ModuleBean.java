package dev.environment.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.environment.annotation.Module;

/**
 * detail: {@link Module} 实体类
 * @author Ttt
 */
public class ModuleBean
        implements Serializable {

    // 模块名
    private final String                name;
    // 模块别名
    private final String                alias;
    // 模块下的环境集合
    private final List<EnvironmentBean> environments = new ArrayList<>();

    public ModuleBean(
            String name,
            String alias
    ) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getAlias() {
        return alias == null ? "" : alias;
    }

    public List<EnvironmentBean> getEnvironments() {
        return environments;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ModuleBean that = (ModuleBean) object;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return alias != null ? alias.equals(that.alias) : that.alias == null;
//        // 不需要判断 List 因为内部 list 会调用 Object ( EnvironmentBean ) equals() 导致死循环
//        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
//        return environments != null ? environments.equals(that.environments) : that.environments == null;
    }

    @Override
    public final int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
//        // 同 equals
//        result = 31 * result + (environments != null ? environments.hashCode() : 0);
        return result;
    }

    private final String JSON_FORMAT = "{\"name\":\"%s\",\"alias\":\"%s\",\"environments\":%s}";

    @Override
    public final String toString() {
        return String.format(JSON_FORMAT, getName(), getAlias(), getEnvironments());
    }
}