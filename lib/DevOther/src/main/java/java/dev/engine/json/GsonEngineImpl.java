package java.dev.engine.json;

import java.dev.other.GsonUtils;
import java.lang.reflect.Type;

import dev.engine.json.IJSONEngine;

/**
 * detail: Gson JSON Engine 实现
 * @author Ttt
 */
public class GsonEngineImpl
        implements IJSONEngine<JSONConfig> {

    // ==========
    // = 转换方法 =
    // ==========

    @Override
    public String toJson(Object object) {
        return GsonUtils.toJson(object);
    }

    @Override
    public String toJson(
            Object object,
            JSONConfig config
    ) {
        return GsonUtils.toJson(object, config.gson);
    }

    @Override
    public <T> T fromJson(
            String json,
            Class<T> classOfT
    ) {
        return GsonUtils.fromJson(json, classOfT);
    }

    @Override
    public <T> T fromJson(
            String json,
            Class<T> classOfT,
            JSONConfig config
    ) {
        return GsonUtils.fromJson(json, classOfT, config.gson);
    }

    @Override
    public <T> T fromJson(
            String json,
            Type typeOfT
    ) {
        return GsonUtils.fromJson(json, typeOfT);
    }

    @Override
    public <T> T fromJson(
            String json,
            Type typeOfT,
            JSONConfig config
    ) {
        return GsonUtils.fromJson(json, typeOfT, config.gson);
    }

    // ==========
    // = 其他方法 =
    // ==========

    @Override
    public boolean isJSON(String json) {
        return GsonUtils.isJSON(json);
    }

    @Override
    public boolean isJSONObject(String json) {
        return GsonUtils.isJSONObject(json);
    }

    @Override
    public boolean isJSONArray(String json) {
        return GsonUtils.isJSONArray(json);
    }

    @Override
    public String toJsonIndent(String json) {
        return GsonUtils.toJsonIndent(json);
    }

    @Override
    public String toJsonIndent(
            String json,
            JSONConfig config
    ) {
        return GsonUtils.toJsonIndent(json, config.gson);
    }

    @Override
    public String toJsonIndent(Object object) {
        return GsonUtils.toJsonIndent(object);
    }

    @Override
    public String toJsonIndent(
            Object object,
            JSONConfig config
    ) {
        return GsonUtils.toJsonIndent(object, config.gson);
    }
}