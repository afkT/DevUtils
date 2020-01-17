package dev.environment.compiler;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    // 方法名
    static final String METHOD_GET_MODULE_LIST = "getModuleList";
    static final String METHOD_GET_MODULE_ENVIRONMENTS_LIST = "getEnvironments";
    static final String METHOD_GET_ENVIRONMENTS_VALUE = "getValue";
    // 变量相关
    static final String VAR_MODULE_PREFIX = "MODULE_";
    static final String VAR_ENVIRONMENT_PREFIX = "ENVIRONMENT_";
    static final String VAR_MODULE_LIST = "MODULE_LIST";
    // 常量字符串
    static final String STR_ENVIRONMENT = "Environment";
    static final String STR_ENVIRONMENT_VALUE = "EnvironmentValue";

    // ================
    // = 内部生成方法 =
    // ================

    // 用于记录 Module 名 Map<Module Name, List<Environment var Name>>
    static final LinkedHashMap<String, List<String>> sModuleNameMap = new LinkedHashMap<>();

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
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc("detail: 环境配置工具类\n")
                .addJavadoc("@author Ttt\n");
        // 创建 DevEnvironment 无参构造函数
        // private DevEnvironment() {}
        MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build();
        return builder.addMethod(constructor);
    }

    /**
     * 创建 DevEnvironment JAVA 文件
     * @param builder       DevEnvironment 类构建对象
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
     * @param builder       DevEnvironment 类构建对象
     * @param moduleElement 使用注解修饰的 Module Element
     * @param processingEnv {@link ProcessingEnvironment}
     */
    public static void builderModule_DATA(final TypeSpec.Builder builder, final Element moduleElement,
                                          final ProcessingEnvironment processingEnv) throws Exception {
        Module moduleAnnotation = moduleElement.getAnnotation(Module.class);
        if (moduleAnnotation == null) return;
        // Module 信息
        String moduleName = moduleElement.getSimpleName().toString();
        String moduleAlias = moduleAnnotation.alias();

        // 获取 Module Release Environment 数据
        Element environmentElement = _getModuleReleaseEnvironment(moduleElement, processingEnv);
        if (environmentElement != null) {
            // 创建 Environment 变量名 List
            List<String> environmentVarNameList = new ArrayList<>();
            sModuleNameMap.put(moduleName, environmentVarNameList);
            // 创建私有常量变量
            // private static final ModuleBean MODULE_XXX = new ModuleBean();
            FieldSpec moduleField = FieldSpec
                    .builder(ModuleBean.class, _getModuleVarName_UpperCase(moduleName))
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
     * @param builder            DevEnvironment 类构建对象
     * @param moduleElement      使用注解修饰的 Module Element
     * @param environmentElement 使用注解修饰的 Environment Element
     */
    public static void builderModuleEnvironment_DATA(final TypeSpec.Builder builder, final Element moduleElement,
                                                     final Element environmentElement) {
        // Module 信息
        String moduleName = moduleElement.getSimpleName().toString();
        // Environment 信息
        Environment environmentAnnotation = environmentElement.getAnnotation(Environment.class);
        String environmentName = environmentElement.getSimpleName().toString();
        String environmentValue = environmentAnnotation.value();
        String environmentAlias = environmentAnnotation.alias();

        // 创建私有常量变量
        // private static final EnvironmentBean ENVIRONMENT_MODULENAME_XXX = new EnvironmentBean();
        FieldSpec environmentField = FieldSpec
                .builder(EnvironmentBean.class, _getEnvironmentVarName_UpperCase(moduleName, environmentName))
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T($S, $S, $S, $L)", EnvironmentBean.class, environmentName,
                        environmentValue, environmentAlias, _getModuleVarName_UpperCase(moduleName))
                .addJavadoc(String.format("[ Environment ] name: %s, alias: %s, [ Module ] name: %s\n",
                        environmentName, environmentAlias, moduleName))
                .build();
        builder.addField(environmentField);

        // 记录 Environment 变量名
        sModuleNameMap.get(moduleName).add(_getEnvironmentVarName_UpperCase(moduleName, environmentName));
    }

    /**
     * 构建 static{} 初始化代码
     * @param builder DevEnvironment 类构建对象
     */
    public static void builderStaticInit(final TypeSpec.Builder builder) {
        // 创建 List 集合变量
        builderList(builder);

        if (!sModuleNameMap.isEmpty()) {
            // 构建 static {} 初始化代码
            CodeBlock.Builder staticCodeBlockBuilder = CodeBlock.builder();

            Iterator<Map.Entry<String, List<String>>> iterator = sModuleNameMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                // Module 名
                String moduleName = entry.getKey();
                // Environment 变量名 ( 因为 release 只能有一个, 这里直接获取 0 )
                String environmentVarName = entry.getValue().get(0);

                // 添加 moduleVarName 到 VAR_MODULE_LIST 中
                staticCodeBlockBuilder.add("\n")
                        .addStatement("$N.add($N)", VAR_MODULE_LIST, _getModuleVarName_UpperCase(moduleName));
                // 添加 Environment 到对应 ModuleBean.getEnvironments() 中
                staticCodeBlockBuilder.addStatement("$N.$N().add($N)", _getModuleVarName_UpperCase(moduleName),
                        METHOD_GET_MODULE_ENVIRONMENTS_LIST, environmentVarName);
            }
            // 创建代码
            builder.addStaticBlock(staticCodeBlockBuilder.build());
        }
    }

    /**
     * 创建 List 集合变量
     * @param builder DevEnvironment 类构建对象
     */
    public static void builderList(final TypeSpec.Builder builder) {
        FieldSpec moduleListField = FieldSpec
                .builder(_getListType(ModuleBean.class), VAR_MODULE_LIST, Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T<$T>()", ArrayList.class, ModuleBean.class)
                .addJavadoc("Module List\n")
                .build();
        builder.addField(moduleListField);
    }

    /**
     * 构建 getXxx 方法代码
     * @param builder DevEnvironment 类构建对象
     */
    public static void builderGetMethod(final TypeSpec.Builder builder) {
        // public static List<ModuleBean> getModuleList() {}
        MethodSpec getModuleListMethod = MethodSpec
                .methodBuilder(METHOD_GET_MODULE_LIST).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(_getListType(ModuleBean.class))
                .addStatement("return $N", VAR_MODULE_LIST)
                .addJavadoc("Get All $N List\n", ModuleBean.class.getSimpleName())
                .addJavadoc("@return List<$N>\n", ModuleBean.class.getSimpleName())
                .build();
        builder.addMethod(getModuleListMethod);

        // 创建 Module Release Environment get 方法
        Iterator<Map.Entry<String, List<String>>> iterator = sModuleNameMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            // Module 名
            String moduleName = entry.getKey();
            // Environment 变量名 ( 因为 release 只能有一个, 这里直接获取 0 )
            String environmentVarName = entry.getValue().get(0);

            String getModuleEnvironmentMethodName = "get" + moduleName + STR_ENVIRONMENT;
            // public static EnvironmentBean getModuleEnvironment() {}
            MethodSpec.Builder getModuleEnvironmentMethodBuilder = MethodSpec
                    .methodBuilder(getModuleEnvironmentMethodName).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(EnvironmentBean.class)
                    .addStatement("return $N", environmentVarName)
                    .addJavadoc("Get $N [ Module ] Release Environment Bean\n", moduleName)
                    .addJavadoc("@return $N [ Module ] Release Environment Bean\n", moduleName);
            builder.addMethod(getModuleEnvironmentMethodBuilder.build());

            // public static String getModuleEnvironmentValue() {}
            String getModuleEnvironmentValueMethodName = "get" + moduleName + STR_ENVIRONMENT_VALUE;
            MethodSpec.Builder getModuleEnvironmentValueMethodBuilder = MethodSpec
                    .methodBuilder(getModuleEnvironmentValueMethodName).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(String.class)
                    .addStatement("return $N.$N()", environmentVarName, METHOD_GET_ENVIRONMENTS_VALUE)
                    .addJavadoc("Get $N [ Module ] Release Environment Value\n", moduleName)
                    .addJavadoc("@return $N [ Module ] Release Environment Value\n", moduleName);
            builder.addMethod(getModuleEnvironmentValueMethodBuilder.build());
        }
    }

    // ============
    // = 其他方法 =
    // ============

    /**
     * 获取 Module Release Environment 数据
     * @param moduleElement 使用注解修饰的 Module Element
     * @param processingEnv {@link ProcessingEnvironment}
     * @return Module Release Environment 数据
     */
    private static Element _getModuleReleaseEnvironment(final Element moduleElement,
                                                        final ProcessingEnvironment processingEnv) throws Exception {
        Element environmentElement = null;
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers((TypeElement) moduleElement);
        for (Element member : allMembers) {
            Environment environmentAnnotation = member.getAnnotation(Environment.class);
            if (environmentAnnotation == null) continue;

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

    /**
     * 获取 List Type
     * @param type Bean.class
     * @return List<Bean> Type
     */
    private static Type _getListType(final Class<?> type) {
        return new ParameterizedTypeImpl(new Type[]{type}, null, List.class);
    }

    /**
     * 获取 Module 拼接变量名
     * @param moduleName Module Name
     * @return Module 拼接变量名
     */
    private static String _getModuleVarName_UpperCase(final String moduleName) {
        return VAR_MODULE_PREFIX + moduleName.toUpperCase();
    }

    /**
     * 获取 Environment 拼接变量名
     * @param moduleName      Module Name
     * @param environmentName Environment Name
     * @return Environment 拼接变量名
     */
    private static String _getEnvironmentVarName_UpperCase(final String moduleName, final String environmentName) {
        return VAR_ENVIRONMENT_PREFIX + moduleName.toUpperCase() + "_" + environmentName.toUpperCase();
    }
}