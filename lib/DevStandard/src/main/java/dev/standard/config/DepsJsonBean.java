package dev.standard.config;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import dev.utils.DevFinal;

/**
 * detail: Dpes json 映射实体类
 * @author Ttt
 */
public final class DepsJsonBean {

    private DepsJsonBean() {
    }

    // 映射 Map
    private LinkedHashMap<String, Map<String, String>> mDepsMaps = new LinkedHashMap<>();

    // 格式化字符串
    public static final String FORMAT_ANNOTATION   = "\t// %s";
    public static final String FORMAT_DEPENDENCIES = "\t%s '%s'";
    public static final String IMPLEMENTATION      = "implementation";

    /**
     * 快捷映射获取方法
     * @param json JSON 数据
     * @return {@link DepsJsonBean}
     */
    public static DepsJsonBean get(final String json) {
        DepsJsonBean depsJsonBean = new DepsJsonBean();
        // 获取 Value Type
        Type valueType = TypeToken.getParameterized(LinkedHashMap.class, String.class, String.class).getType();
        // JSON 映射 Type
        Type type = TypeToken.getParameterized(LinkedHashMap.class, String.class, valueType).getType();
        // 映射 Map
        depsJsonBean.mDepsMaps = new GsonBuilder().setPrettyPrinting().create().fromJson(json, type);
        return depsJsonBean;
    }

    /**
     * 获取对应 Key Map
     * @param key Key
     * @return Map
     */
    public Map<String, String> map(final String key) {
        return mDepsMaps.get(key);
    }

    /**
     * 获取 Gradle Dependencies 依赖信息
     * @param mark 标记符
     * @param maps 第三方库列表
     * @return Gradle Dependencies 依赖信息
     */
    public String getDependencies(
            final String mark,
            final Map<String, String> maps
    ) {
        if (maps != null && !maps.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                builder.append(DevFinal.NEW_LINE_STR);
                builder.append(String.format(FORMAT_ANNOTATION, entry.getKey()));
                builder.append(DevFinal.NEW_LINE_STR);
                builder.append(String.format(FORMAT_DEPENDENCIES, mark, entry.getValue()));
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 获取 Gradle Dependencies 全部依赖信息
     * @param mark 标记符
     * @return Gradle Dependencies 全部依赖信息
     */
    public String getAllDependencies(final String mark) {
        return getAllDependencies(mark, true);
    }

    /**
     * 获取 Gradle Dependencies 全部依赖信息
     * @param mark    标记符
     * @param divider 是否进行分割
     * @return Gradle Dependencies 全部依赖信息
     */
    public String getAllDependencies(
            final String mark,
            final boolean divider
    ) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> entry : mDepsMaps.entrySet()) {
            if (divider) {
                builder.append(DevFinal.NEW_LINE_STR_X2)
                        .append(String.format(FORMAT_ANNOTATION, ("= " + entry.getKey() + " =")))
                        .append(DevFinal.NEW_LINE_STR);
            }
            builder.append(getDependencies(mark, entry.getValue()));
        }
        return builder.toString();
    }
}