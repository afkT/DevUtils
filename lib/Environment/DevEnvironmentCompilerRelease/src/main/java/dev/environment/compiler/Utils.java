package dev.environment.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

/**
 * detail: 内部工具类
 * @author Ttt
 */
final class Utils {

    // 包名
    static String PACKAGE_NAME = "dev.environment";
    // 工具类文件名
    static String ENVIRONMENT_FILE_NAME = "DevEnvironment";

    // 获取 Module 列表方法名
    static String METHOD_NAME_GET_MODULE_LIST = "getModuleList";
    // 属性名
    static String VAR_ENVIRONMENT_VALUE_SUFFIX = "Value";
    static String VAR_ENVIRONMENT_NAME_SUFFIX = "Name";
    static String VAR_ENVIRONMENT_ALIAS_SUFFIX = "Alias";

    // ================
    // = 内部生成方法 =
    // ================

    // ========
    // = 通用 =
    // ========

    /**
     * 构建 DevEnvironment 类对象
     * @return {@link TypeSpec.Builder}
     */
    public static TypeSpec.Builder builderDevEnvironment_Class() {
        // 创建 DevEnvironment 类
        TypeSpec.Builder builder = TypeSpec.classBuilder(Utils.ENVIRONMENT_FILE_NAME)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        // 添加注释
        builder.addJavadoc("detail: 环境配置工具类")
            .addJavadoc("\n@author Ttt\n");
        return builder;
    }

    /**
     * 创建 DevEnvironment JAVA 文件
     * @param builder DevEnvironment 构建类对象
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createDevEnvironmentJavaFile(final TypeSpec.Builder builder,
                                                       final ProcessingEnvironment processingEnv) {
        JavaFile javaFile = JavaFile.builder(Utils.PACKAGE_NAME, builder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // =

    /**
     * 创建 DevEnvironment 无参构造函数
     * @return {@link TypeSpec.Builder}
     */
    public static void builderDevEnvironmentConstructor_Method(final TypeSpec.Builder builder) {
        MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build();
        builder.addMethod(constructor);
    }
}