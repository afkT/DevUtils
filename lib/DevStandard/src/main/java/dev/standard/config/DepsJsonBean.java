package dev.standard.config;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

import dev.utils.common.StringUtils;

/**
 * detail: Dpes json 映射实体类
 * @author Ttt
 */
public final class DepsJsonBean {

    private DepsJsonBean() {
    }

    // 映射 Map
    public LinkedHashMap<String, LinkedHashMap<String, String>> mDepsMaps = new LinkedHashMap<>();

    // 格式化字符串
    public final        String FORMAT_ANNOTATION   = "\t// %s";
    public final        String FORMAT_DEPENDENCIES = "\t%s '%s'";
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
    public LinkedHashMap<String, String> map(final String key) {
        return mDepsMaps.get(key);
    }

    /**
     * 获取 Gradle Dependencies 依赖信息
     * @param mark 标记符
     * @param maps 第三方库列表
     * @return Gradle Dependencies 依赖信息
     */
    public String getDependencies(final String mark, final LinkedHashMap<String, String> maps) {
        if (maps != null && !maps.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String key : maps.keySet()) {
                String value = maps.get(key);

                builder.append(StringUtils.NEW_LINE_STR);
                builder.append(String.format(FORMAT_ANNOTATION, key));
                builder.append(StringUtils.NEW_LINE_STR);
                builder.append(String.format(FORMAT_DEPENDENCIES, mark, value));
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
    public String getAllDependencies(final String mark, final boolean divider) {
        StringBuilder builder = new StringBuilder();
        for (String key : mDepsMaps.keySet()) {
            if (divider) {
                builder.append(StringUtils.NEW_LINE_STR_X2)
                        .append(String.format(FORMAT_ANNOTATION, ("= " + key + " =")))
                        .append(StringUtils.NEW_LINE_STR);
            }
            LinkedHashMap<String, String> maps = mDepsMaps.get(key);
            builder.append(getDependencies(mark, maps));
        }
        return builder.toString();
    }
}