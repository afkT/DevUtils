package dev.environment.compiler;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import dev.environment.annotation.Environment;
import dev.environment.annotation.Module;
import dev.environment.bean.EnvironmentBean;
import dev.environment.bean.ModuleBean;

/**
 * detail: 内部工具类
 * @author Ttt
 */
final class Utils {

    // 包名
    static final String PACKAGE_NAME = "dev.environment";
    // 工具类文件名
    static final String ENVIRONMENT_FILE_NAME = "DevEnvironment";

    // 获取 Module 列表方法名
    static final String METHOD_NAME_GET_MODULE_LIST = "getModuleList";
    // 变量相关
    static final String VAR_MODULE_PREFIX = "MODULE_";
    static final String VAR_ENVIRONMENT_PREFIX = "ENVIRONMENT_";
//    static final String VAR_ENVIRONMENT_VALUE_SUFFIX = "Value";
//    static final String VAR_ENVIRONMENT_NAME_SUFFIX = "Name";
//    static final String VAR_ENVIRONMENT_ALIAS_SUFFIX = "Alias";

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
        // 创建 DevEnvironment 无参构造函数 private DevEnvironment(){}
        MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build();
        return builder.addMethod(constructor);
    }

    /**
     * 创建 DevEnvironment JAVA 文件
     * @param builder DevEnvironment 类构建对象
     * @param processingEnv {@link ProcessingEnvironment}
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
     * 创建 Module 数据
     * @param builder DevEnvironment 构建类对象
     * @param moduleElement 使用注解修饰的 Module Element
     * @param processingEnv {@link ProcessingEnvironment}
     */
    public static void builderModule_DATA(final TypeSpec.Builder builder, final Element moduleElement,
                                          final ProcessingEnvironment processingEnv) throws Exception {
        Module moduleAnnotation = moduleElement.getAnnotation(Module.class);
        if (moduleAnnotation == null) return;
        // Module 信息
        String moduleName = moduleElement.getSimpleName().toString();
        String moduleNameUpperCase = moduleName.toUpperCase();
        String moduleAlias = moduleAnnotation.alias();

        // 获取 Module Release Environment 数据
        Element environmentElement = getModuleReleaseEnvironment(moduleElement, processingEnv);
        if (environmentElement != null) {
            // 创建私有常量变量
            // private static final ModuleBean MODULE_XXX = new ModuleBean();
            FieldSpec moduleField = FieldSpec
                .builder(ModuleBean.class, VAR_MODULE_PREFIX + moduleNameUpperCase)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T($S, $S)", ModuleBean.class, moduleName, moduleAlias)
                .addJavadoc(String.format("[ Module ] name: %s, alias: %s\n", moduleName, moduleAlias))
                .build();
            builder.addField(moduleField);

            // 创建 Module Environment 数据
            builderModuleEnvironment_DATA(builder, moduleElement, environmentElement);
        }
    }

    /**
     * 创建 Module Environment 数据
     * @param builder DevEnvironment 构建类对象
     * @param moduleElement 使用注解修饰的 Module Element
     * @param environmentElement 使用注解修饰的 Environment Element
     */
    public static void builderModuleEnvironment_DATA(final TypeSpec.Builder builder, final Element moduleElement,
                                                     final Element environmentElement) {
        // Module 信息
        String moduleName = moduleElement.getSimpleName().toString();
        String moduleNameUpperCase = moduleName.toUpperCase();
        // Environment 信息
        Environment environmentAnnotation = environmentElement.getAnnotation(Environment.class);
        String environmentName = environmentElement.getSimpleName().toString();
        String environmentNameUpperCase = environmentName.toUpperCase();
        String environmentValue = environmentAnnotation.value();
        String environmentAlias = environmentAnnotation.alias();

        // 创建私有常量变量
        // private static final EnvironmentBean ENVIRONMENT_MODULENAME_XXX = new EnvironmentBean();
        FieldSpec environmentField = FieldSpec
            .builder(EnvironmentBean.class, VAR_ENVIRONMENT_PREFIX + moduleNameUpperCase + "_" + environmentNameUpperCase)
            .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
            .initializer("new $T($S, $S, $S, $L)", EnvironmentBean.class, environmentName,
                environmentValue, environmentAlias, VAR_MODULE_PREFIX + moduleNameUpperCase)
            .addJavadoc(String.format("[ Environment ] name: %s, alias: %s, [ Module ] name: %s\n", environmentName, environmentAlias, moduleName))
            .build();
        builder.addField(environmentField);
    }

    // ============
    // = 判断方法 =
    // ============

    /**
     * 获取 Module 中的 Release Environment 数据
     * @param moduleElement 使用注解修饰的 Module Element
     * @param processingEnv {@link ProcessingEnvironment}
     * @return Module Release Environment 数据
     */
    public static Element getModuleReleaseEnvironment(final Element moduleElement, final ProcessingEnvironment processingEnv) throws Exception {
        Element environmentElement = null;
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers((TypeElement) moduleElement);
        for (Element member : allMembers) {
            Environment environmentAnnotation = member.getAnnotation(Environment.class);
            if (environmentAnnotation == null) continue;
            // 判断是否存在
            if (environmentAnnotation.isRelease()) {
                if (environmentElement != null) { // 每个 Module 只能有一个 release 环境配置
                    String moduleName = moduleElement.getSimpleName().toString();
                    throw new Exception(moduleName + " module can be only one release environment configuration");
                }
                environmentElement = member;
            }
        }
        return environmentElement;
    }
}