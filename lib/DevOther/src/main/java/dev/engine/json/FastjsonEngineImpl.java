package dev.engine.json;

import java.lang.reflect.Type;

import dev.other.FastjsonUtils;

/**
 * detail: Fastjson JSON Engine 实现
 * @author Ttt
 */
public class FastjsonEngineImpl
        implements IJSONEngine<JSONConfig> {

    // ===========
    // = 转换方法 =
    // ===========

    @Override
    public String toJson(Object object) {
        return FastjsonUtils.toJson(object);
    }

    @Override
    public String toJson(
            Object object,
            JSONConfig config
    ) {
        return FastjsonUtils.toJson(object);
    }

    @Override
    public <T> T fromJson(
            String json,
            Class<T> classOfT
    ) {
        return FastjsonUtils.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(
            String json,
            Class<T> classOfT,
            JSONConfig config
    ) {
        return FastjsonUtils.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(
            String json,
            Type typeOfT
    ) {
        return FastjsonUtils.fromJson(json, typeOfT);
    }

    @Override
    public <T> T fromJson(
            String json,
            Type typeOfT,
            JSONConfig config
    ) {
        return FastjsonUtils.fromJson(json, typeOfT);
    }

    // ===========
    // = 其他方法 =
    // ===========

    @Override
    public boolean isJSON(String json) {
        return FastjsonUtils.isJSON(json);
    }

    @Override
    public boolean isJSONObject(String json) {
        return FastjsonUtils.isJSONObject(json);
    }

    @Override
    public boolean isJSONArray(String json) {
        return FastjsonUtils.isJSONArray(json);
    }

    @Override
    public String toJsonIndent(String json) {
        return FastjsonUtils.toJsonIndent(json);
    }

    @Override
    public String toJsonIndent(
            String json,
            JSONConfig config
    ) {
        return FastjsonUtils.toJsonIndent(json);
    }

    @Override
    public String toJsonIndent(Object object) {
        return FastjsonUtils.toJsonIndent(object);
    }

    @Override
    public String toJsonIndent(
            Object object,
            JSONConfig config
    ) {
        return FastjsonUtils.toJsonIndent(object);
    }
}