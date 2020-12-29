package dev.environment.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
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
import dev.environment.listener.OnEnvironmentChangeListener;
import dev.environment.log.LogUtils;
import dev.environment.type.ParameterizedTypeImpl;

/**
 * detail: 内部工具类
 * @author Ttt
 */
final class Utils {

    // 包名
    static final String PACKAGE_NAME          = "dev.environment";
    // 工具类文件名
    static final String ENVIRONMENT_FILE_NAME = "DevEnvironment";

    // 方法名
    static final String   METHOD_RESET                                = "reset";
    static final String   METHOD_IS_RELEASE                           = "isRelease";
    static final String   METHOD_GET_MODULE_LIST                      = "getModuleList";
    static final String   METHOD_GET_MODULE_ENVIRONMENTS_LIST         = "getEnvironments";
    static final String   METHOD_GET_ENVIRONMENTS_VALUE               = "getValue";
    static final String   METHOD_ADD_ONENVIRONMENT_CHANGE_LISTENER    = "addOnEnvironmentChangeListener";
    static final String   METHOD_REMOVE_ONENVIRONMENT_CHANGE_LISTENER = "removeOnEnvironmentChangeListener";
    static final String   METHOD_CLEAR_ONENVIRONMENT_CHANGE_LISTENER  = "clearOnEnvironmentChangeListener";
    static final String   METHOD_GET_STORAGE_DIR                      = "getStorageDir";
    static final String   METHOD_DELETE_STORAGE_DIR                   = "deleteStorageDir";
    static final String   METHOD_IS_ANNOTATION                        = "is%sAnnotation";
    // 变量相关
    static final String   VAR_MODULE_PREFIX                           = "MODULE_";
    static final String   VAR_ENVIRONMENT_PREFIX                      = "ENVIRONMENT_";
    static final String   VAR_MODULELIST                              = "moduleList";
    static final String   VAR_MODULE_LIST                             = "MODULE_LIST";
    static final String   VAR_CONTEXT                                 = "context";
    static final String   VAR_NEW_ENVIRONMENT                         = "newEnvironment";
    static final String   VAR_LISTENER                                = "listener";
    static final String   VAR_FILE_NAME                               = "fileName";
    // 常量字符串
    static final String   STR_MODULE                                  = "Module";
    static final String   STR_ENVIRONMENT                             = "Environment";
    static final String   STR_ENVIRONMENT_VALUE                       = "EnvironmentValue";
    static final String   STR_RELEASE_ENVIRONMENT                     = "ReleaseEnvironment";
    // 其他
    static final TypeName TYPE_NAME_CONTEXT                           = ClassName.get("android.content", "Context");

    // ===============
    // = 内部生成方法 =
    // ===============

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
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(ENVIRONMENT_FILE_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addJavadoc("detail: 环境配置工具类\n")
                .addJavadoc("@author Ttt\n");
        // 创建 DevEnvironment 无参构造函数
        // private DevEnvironment() {}
        MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build();
        return classBuilder.addMethod(constructor);
    }

    /**
     * 创建 DevEnvironment JAVA 文件
     * @param classBuilder  DevEnvironment 类构建对象
     * @param processingEnv {@link ProcessingEnvironment}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean createDevEnvironmentJavaFile(
            final TypeSpec.Builder classBuilder,
            final ProcessingEnvironment processingEnv
    ) {
        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, classBuilder.build()).build();
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
     * @param classBuilder  DevEnvironment 类构建对象
     * @param moduleElement 使用注解修饰的 Module Element
     * @param processingEnv {@link ProcessingEnvironment}
     */
    public static void builderModule_DATA(
            final TypeSpec.Builder classBuilder,
            final Element moduleElement,
            final ProcessingEnvironment processingEnv
    )
            throws Exception {
        Module moduleAnnotation = moduleElement.getAnnotation(Module.class);
        if (moduleAnnotation == null) return;
        // Module 信息
        String moduleName  = moduleElement.getSimpleName().toString();
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
            classBuilder.addField(moduleField);

            // 创建 Module Environment 数据
            builderModuleEnvironment_DATA(classBuilder, moduleElement, environmentElement);
        }
    }

    /**
     * 创建 Module Environment 数据
     * @param classBuilder       DevEnvironment 类构建对象
     * @param moduleElement      使用注解修饰的 Module Element
     * @param environmentElement 使用注解修饰的 Environment Element
     */
    public static void builderModuleEnvironment_DATA(
            final TypeSpec.Builder classBuilder,
            final Element moduleElement,
            final Element environmentElement
    ) {
        // Module 信息
        String moduleName = moduleElement.getSimpleName().toString();
        // Environment 信息
        Environment environmentAnnotation = environmentElement.getAnnotation(Environment.class);
        String      environmentName       = environmentElement.getSimpleName().toString();
        String      environmentValue      = environmentAnnotation.value();
        String      environmentAlias      = environmentAnnotation.alias();

        // 创建私有常量变量
        // private static final EnvironmentBean ENVIRONMENT_MODULENAME_XXX = new EnvironmentBean();
        FieldSpec environmentField = FieldSpec
                .builder(EnvironmentBean.class, _getEnvironmentVarName_UpperCase(moduleName, environmentName))
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T($S, $S, $S, $N)", EnvironmentBean.class, environmentName,
                        environmentValue, environmentAlias, _getModuleVarName_UpperCase(moduleName))
                .addJavadoc(String.format("[ Environment ] name: %s, alias: %s, [ Module ] name: %s\n",
                        environmentName, environmentAlias, moduleName))
                .build();
        classBuilder.addField(environmentField);

        // 记录 Environment 变量名 ( 传入的 environmentElement ) 属于 Release Environment Element 存储在 index 0
        sModuleNameMap.get(moduleName).add(_getEnvironmentVarName_UpperCase(moduleName, environmentName));
    }

    /**
     * 构建 Reset 方法
     * @param classBuilder DevEnvironment 类构建对象
     */
    public static void builderResetMethod(final TypeSpec.Builder classBuilder) {
        // 构建 reset 实现代码
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        codeBlockBuilder.add("if ($N != null && $N($N)) {\n", VAR_CONTEXT, METHOD_DELETE_STORAGE_DIR, VAR_CONTEXT);
        codeBlockBuilder.add("    return true;\n");
        codeBlockBuilder.add("}\n");

        // public static final void reset() {}
        MethodSpec resetMethod = MethodSpec
                .methodBuilder(METHOD_RESET)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addParameter(TYPE_NAME_CONTEXT, VAR_CONTEXT, Modifier.FINAL)
                .addCode(codeBlockBuilder.build())
                .returns(Boolean.class)
                .addStatement("return false")
                .addJavadoc("重置操作\n")
                .addJavadoc("<p>Reset Operating\n")
                .addJavadoc("@param $N {@link Context}\n", VAR_CONTEXT)
                .addJavadoc("@return {@code true} success, {@code false} fail\n")
                .build();
        classBuilder.addMethod(resetMethod);
    }

    /**
     * 构建 isRelease 方法
     * @param classBuilder DevEnvironment 类构建对象
     */
    public static void builderIsReleaseMethod(final TypeSpec.Builder classBuilder) {
        // public static final Boolean isRelease() {}
        MethodSpec isReleaseMethod = MethodSpec
                .methodBuilder(METHOD_IS_RELEASE)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .returns(Boolean.class)
                .addStatement("return true")
                .addJavadoc("是否使用 releaseAnnotationProcessor 构建\n")
                .addJavadoc("<p>Whether Release Annotation Processor Compile\n")
                .addJavadoc("@return {@code true} yes, {@code false} no\n")
                .build();
        classBuilder.addMethod(isReleaseMethod);
    }

    /**
     * 构建 static{} 初始化代码
     * @param classBuilder DevEnvironment 类构建对象
     */
    public static void builderStaticInit(final TypeSpec.Builder classBuilder) {
        // 创建 Module List 集合变量
        FieldSpec moduleListField = FieldSpec
                .builder(_getListType(ModuleBean.class), VAR_MODULE_LIST, Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
//                .initializer("new $T<$T>()", ArrayList.class, ModuleBean.class)
                .addJavadoc("Module List\n")
                .build();
        classBuilder.addField(moduleListField);

        if (!sModuleNameMap.isEmpty()) {
            // 构建 static {} 初始化代码
            CodeBlock.Builder staticCodeBlockBuilder = CodeBlock.builder();
            // 创建 module List 集合 VAR_MODULELIST
            staticCodeBlockBuilder.addStatement("List<$T> $N = new $T<>()", ModuleBean.class,
                    VAR_MODULELIST, ArrayList.class);

            Iterator<Map.Entry<String, List<String>>> iterator = sModuleNameMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                // Module 名
                String moduleName = entry.getKey();
                // Environment 变量名 ( 因为 release 只能有一个, 这里直接获取 0 )
                String environmentVarName = entry.getValue().get(0);

                // 添加 moduleVarName 到 VAR_MODULELIST 中
                staticCodeBlockBuilder.add("\n")
                        .addStatement("$N.add($N)", VAR_MODULELIST, _getModuleVarName_UpperCase(moduleName));
                // 添加 Environment 到对应 ModuleBean.getEnvironments() 中
                staticCodeBlockBuilder.addStatement("$N.$N().add($N)", _getModuleVarName_UpperCase(moduleName),
                        METHOD_GET_MODULE_ENVIRONMENTS_LIST, environmentVarName);
            }
            // 初始化 Module List 集合变量 VAR_MODULE_LIST
            staticCodeBlockBuilder.add("\n")
                    .addStatement("$N = $T.unmodifiableList($N)", VAR_MODULE_LIST, Collections.class, VAR_MODULELIST);
            // 创建代码
            classBuilder.addStaticBlock(staticCodeBlockBuilder.build());
        }
    }

    /**
     * 构建 getXxx 方法代码
     * @param classBuilder DevEnvironment 类构建对象
     */
    public static void builderGetMethod(final TypeSpec.Builder classBuilder) {
        // public static final List<ModuleBean> getModuleList() {}
        MethodSpec getModuleListMethod = MethodSpec
                .methodBuilder(METHOD_GET_MODULE_LIST)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .returns(_getListType(ModuleBean.class))
                .addStatement("return $N", VAR_MODULE_LIST)
                .addJavadoc("获取全部 $N 配置列表\n", ModuleBean.class.getSimpleName())
                .addJavadoc("<p>Get All $N List\n", ModuleBean.class.getSimpleName())
                .addJavadoc("@return List<$N>\n", ModuleBean.class.getSimpleName())
                .build();
        classBuilder.addMethod(getModuleListMethod);

        // 创建 Module Environment get 方法
        Iterator<Map.Entry<String, List<String>>> iterator = sModuleNameMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            // Module 名
            String moduleName = entry.getKey();
            // Environment 变量名 ( 因为 release 只能有一个, 这里直接获取 0 )
            String environmentVarName = entry.getValue().get(0);

            // public static final ModuleBean getModule() {}
            String getModuleMethodName = "get" + moduleName + STR_MODULE;
            MethodSpec getModuleMethod = MethodSpec
                    .methodBuilder(getModuleMethodName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .returns(ModuleBean.class)
                    .addStatement("return $N", _getModuleVarName_UpperCase(moduleName))
                    .addJavadoc("获取 $N [ Module ] Bean\n", moduleName)
                    .addJavadoc("<p>Get $N [ Module ] Bean\n", moduleName)
                    .addJavadoc("@return $N [ Module ] Bean\n", moduleName)
                    .build();
            classBuilder.addMethod(getModuleMethod);

            // =

            // public static final EnvironmentBean getModuleReleaseEnvironment() {}
            String getModuleReleaseEnvironmentMethodName = "get" + moduleName + STR_RELEASE_ENVIRONMENT;
            MethodSpec getModuleReleaseEnvironmentMethod = MethodSpec
                    .methodBuilder(getModuleReleaseEnvironmentMethodName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .returns(EnvironmentBean.class)
                    .addStatement("return $N", environmentVarName)
                    .addJavadoc("获取 $N [ Module ] Release Environment Bean\n", moduleName)
                    .addJavadoc("<p>Get $N [ Module ] Release Environment Bean\n", moduleName)
                    .addJavadoc("@return $N [ Module ] Release Environment Bean\n", moduleName)
                    .build();
            classBuilder.addMethod(getModuleReleaseEnvironmentMethod);

            // =

            // public static final EnvironmentBean getModuleEnvironment(final Context context) {}
            String getModuleEnvironmentMethodName = "get" + moduleName + STR_ENVIRONMENT;
            MethodSpec getModuleEnvironmentMethod = MethodSpec
                    .methodBuilder(getModuleEnvironmentMethodName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addParameter(TYPE_NAME_CONTEXT, VAR_CONTEXT, Modifier.FINAL)
                    .returns(EnvironmentBean.class)
                    .addStatement("return $N()", getModuleReleaseEnvironmentMethodName)
                    .addJavadoc("获取 $N [ Module ] Selected Environment Bean\n", moduleName)
                    .addJavadoc("<p>Get $N [ Module ] Selected Environment Bean\n", moduleName)
                    .addJavadoc("@param $N {@link Context}\n", VAR_CONTEXT)
                    .addJavadoc("@return $N [ Module ] Selected Environment Bean\n", moduleName)
                    .build();
            classBuilder.addMethod(getModuleEnvironmentMethod);

            // =

            // public static final String getModuleEnvironmentValue(final Context context) {}
            String getModuleEnvironmentValueMethodName = "get" + moduleName + STR_ENVIRONMENT_VALUE;
            MethodSpec getModuleEnvironmentValueMethod = MethodSpec
                    .methodBuilder(getModuleEnvironmentValueMethodName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addParameter(TYPE_NAME_CONTEXT, VAR_CONTEXT, Modifier.FINAL)
                    .returns(String.class)
                    .addStatement("return $N($N).$N()", getModuleEnvironmentMethodName, VAR_CONTEXT, METHOD_GET_ENVIRONMENTS_VALUE)
                    .addJavadoc("获取 $N [ Module ] Selected Environment Value\n", moduleName)
                    .addJavadoc("<p>Get $N [ Module ] Selected Environment Value\n", moduleName)
                    .addJavadoc("@param $N {@link Context}\n", VAR_CONTEXT)
                    .addJavadoc("@return $N [ Module ] Selected Environment Value\n", moduleName)
                    .build();
            classBuilder.addMethod(getModuleEnvironmentValueMethod);

            // =

            // public static final Boolean setModuleEnvironment(final Context context, final EnvironmentBean newEnvironment) {}
            String setModuleEnvironmentMethodName = "set" + moduleName + STR_ENVIRONMENT;
            MethodSpec setModuleEnvironmentMethod = MethodSpec
                    .methodBuilder(setModuleEnvironmentMethodName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addParameter(TYPE_NAME_CONTEXT, VAR_CONTEXT, Modifier.FINAL)
                    .addParameter(EnvironmentBean.class, VAR_NEW_ENVIRONMENT, Modifier.FINAL)
                    .returns(Boolean.class)
                    .addStatement("return false")
                    .addJavadoc("设置 $N [ Module ] Selected Environment Bean\n", moduleName)
                    .addJavadoc("<p>Set $N [ Module ] Selected Environment Bean\n", moduleName)
                    .addJavadoc("@param $N {@link Context}\n", VAR_CONTEXT)
                    .addJavadoc("@param $N environment bean\n", VAR_NEW_ENVIRONMENT)
                    .addJavadoc("@return {@code true} success, {@code false} fail\n")
                    .build();
            classBuilder.addMethod(setModuleEnvironmentMethod);

            // =

            // public static final Boolean resetModule(final Context context) {}
            String resetModuleMethodName = METHOD_RESET + moduleName;
            MethodSpec resetModuleMethod = MethodSpec
                    .methodBuilder(resetModuleMethodName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addParameter(TYPE_NAME_CONTEXT, VAR_CONTEXT, Modifier.FINAL)
                    .returns(Boolean.class)
                    .addStatement("return false")
                    .addJavadoc("重置 $N [ Module ] Selected Environment Bean\n", moduleName)
                    .addJavadoc("<p>Reset $N [ Module ] Selected Environment Bean\n", moduleName)
                    .addJavadoc("@param $N {@link Context}\n", VAR_CONTEXT)
                    .addJavadoc("@return {@code true} success, {@code false} fail\n")
                    .build();
            classBuilder.addMethod(resetModuleMethod);

            // =

            // public static final Boolean isModuleAnnotation(final Context context) {}
            String isModuleAnnotationMethodName = String.format(METHOD_IS_ANNOTATION, moduleName);
            MethodSpec isModuleAnnotationMethod = MethodSpec
                    .methodBuilder(isModuleAnnotationMethodName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addParameter(TYPE_NAME_CONTEXT, VAR_CONTEXT, Modifier.FINAL)
                    .returns(Boolean.class)
                    .addStatement("return true")
                    .addJavadoc("是否 $N [ Module ] Annotation Environment Bean\n", moduleName)
                    .addJavadoc("<p>Whether $N [ Module ] Annotation Environment Bean\n", moduleName)
                    .addJavadoc("@param $N {@link Context}\n", VAR_CONTEXT)
                    .addJavadoc("@return {@code true} success, {@code false} fail\n")
                    .build();
            classBuilder.addMethod(isModuleAnnotationMethod);
        }
    }

    /**
     * 构建模块环境改变触发事件方法
     * @param classBuilder DevEnvironment 类构建对象
     */
    public static void builderEnvironmentChangeListener(final TypeSpec.Builder classBuilder) {
        // public static final Boolean addOnEnvironmentChangeListener(final OnEnvironmentChangeListener listener) {}
        MethodSpec addOnEnvironmentChangeListenerMethod = MethodSpec
                .methodBuilder(METHOD_ADD_ONENVIRONMENT_CHANGE_LISTENER)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addParameter(OnEnvironmentChangeListener.class, VAR_LISTENER, Modifier.FINAL)
                .returns(Boolean.class)
                .addStatement("return false")
                .addJavadoc("添加模块环境改变触发事件\n")
                .addJavadoc("<p>Add Environment Change Listener\n")
                .addJavadoc("@param $N environment change listener\n", VAR_LISTENER)
                .addJavadoc("@return {@code true} success, {@code false} fail\n")
                .build();
        classBuilder.addMethod(addOnEnvironmentChangeListenerMethod);

        // =

        // public static final Boolean removeOnEnvironmentChangeListener(final OnEnvironmentChangeListener listener) {}
        MethodSpec removeOnEnvironmentChangeListenerMethod = MethodSpec
                .methodBuilder(METHOD_REMOVE_ONENVIRONMENT_CHANGE_LISTENER)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addParameter(OnEnvironmentChangeListener.class, VAR_LISTENER, Modifier.FINAL)
                .returns(Boolean.class)
                .addStatement("return false")
                .addJavadoc("移除模块环境改变触发事件\n")
                .addJavadoc("<p>Remove Environment Change Listener\n")
                .addJavadoc("@param $N environment change listener\n", VAR_LISTENER)
                .addJavadoc("@return {@code true} success, {@code false} fail\n")
                .build();
        classBuilder.addMethod(removeOnEnvironmentChangeListenerMethod);

        // =

        // public static final Boolean clearOnEnvironmentChangeListener() {}
        MethodSpec clearOnEnvironmentChangeListenerMethod = MethodSpec
                .methodBuilder(METHOD_CLEAR_ONENVIRONMENT_CHANGE_LISTENER)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .returns(Boolean.class)
                .addStatement("return false")
                .addJavadoc("清空模块环境改变触发事件\n")
                .addJavadoc("<p>Clear All Environment Change Listener\n")
                .addJavadoc("@return {@code true} success, {@code false} fail\n")
                .build();
        classBuilder.addMethod(clearOnEnvironmentChangeListenerMethod);
    }

    /**
     * 构建存储相关方法
     * @param classBuilder DevEnvironment 类构建对象
     */
    public static void builderStorageMethod(final TypeSpec.Builder classBuilder) {
        // 构建 getStorageDir 实现代码
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        codeBlockBuilder.add("try {\n");
        codeBlockBuilder.add("    File file = new File($N.getCacheDir(), $S);\n", VAR_CONTEXT, ENVIRONMENT_FILE_NAME);
        codeBlockBuilder.add("    if (!file.exists()) file.mkdirs();\n");
        codeBlockBuilder.add("    return file;\n");
        codeBlockBuilder.add("} catch (Exception e) {\n");
        codeBlockBuilder.add("    $T.printStackTrace(e);\n", LogUtils.class);
        codeBlockBuilder.add("}\n");

        // private static final File getStorageDir(final Context context) {}
        MethodSpec getStorageDirMethod = MethodSpec
                .methodBuilder(METHOD_GET_STORAGE_DIR)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .addParameter(TYPE_NAME_CONTEXT, VAR_CONTEXT, Modifier.FINAL)
                .returns(File.class)
                .addCode(codeBlockBuilder.build())
                .addStatement("return null")
                .addJavadoc("获取环境配置存储路径 - path /data/data/package/cache/$N\n", ENVIRONMENT_FILE_NAME)
                .addJavadoc("<p>Get Environment Configure Storage Dir - path /data/data/package/cache/$N\n", ENVIRONMENT_FILE_NAME)
                .addJavadoc("@param $N {@link Context}\n", VAR_CONTEXT)
                .addJavadoc("@return /data/data/package/cache/$N\n", ENVIRONMENT_FILE_NAME)
                .build();
        classBuilder.addMethod(getStorageDirMethod);

        // =

        // 构建 deleteStorageDir 实现代码
        codeBlockBuilder = CodeBlock.builder();
        codeBlockBuilder.add("try {\n");
        codeBlockBuilder.add("    File storage = $N($N);\n", METHOD_GET_STORAGE_DIR, VAR_CONTEXT);
        codeBlockBuilder.add("    if (storage != null) {\n");
        codeBlockBuilder.add("        String[] strs = storage.list();\n");
        codeBlockBuilder.add("        for (String fileName : strs) {\n");
        codeBlockBuilder.add("            File file = new File(storage, fileName);\n");
        codeBlockBuilder.add("            if (!file.isDirectory()) file.delete();\n");
        codeBlockBuilder.add("        }\n");
        codeBlockBuilder.add("        return true;\n");
        codeBlockBuilder.add("    }\n");
        codeBlockBuilder.add("} catch (Exception e) {\n");
        codeBlockBuilder.add("    $T.printStackTrace(e);\n", LogUtils.class);
        codeBlockBuilder.add("}\n");

        // private static final Boolean deleteStorageDir(final Context context) {}
        MethodSpec deleteStorageDirMethod = MethodSpec
                .methodBuilder(METHOD_DELETE_STORAGE_DIR)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .addParameter(TYPE_NAME_CONTEXT, VAR_CONTEXT, Modifier.FINAL)
                .returns(Boolean.class)
                .addCode(codeBlockBuilder.build())
                .addStatement("return false")
                .addJavadoc("删除环境存储配置文件\n")
                .addJavadoc("<p>Delete Environment Storage Configure File\n")
                .addJavadoc("@param $N {@link Context}\n", VAR_CONTEXT)
                .addJavadoc("@return {@code true} success, {@code false} fail\n")
                .build();
        classBuilder.addMethod(deleteStorageDirMethod);
    }

    // ===========
    // = 其他方法 =
    // ===========

    /**
     * 获取 Module Release Environment 数据
     * @param moduleElement 使用注解修饰的 Module Element
     * @param processingEnv {@link ProcessingEnvironment}
     * @return Module Release Environment 数据
     */
    private static Element _getModuleReleaseEnvironment(
            final Element moduleElement,
            final ProcessingEnvironment processingEnv
    )
            throws Exception {
        Element                 environmentElement = null;
        List<? extends Element> allMembers         = processingEnv.getElementUtils().getAllMembers((TypeElement) moduleElement);
        for (Element member : allMembers) {
            Environment environmentAnnotation = member.getAnnotation(Environment.class);
            if (environmentAnnotation == null) continue;

            if (environmentAnnotation.isRelease()) {
                if (environmentElement != null) { // 每个 Module 只能有一个 release 环境配置
                    String moduleName = moduleElement.getSimpleName().toString();
                    throw new Exception(moduleName + " module can be only one release environment configuration ( 每个 Module 只能有一个 release 环境配置 )");
                }
                environmentElement = member;
            }
        }
        if (environmentElement == null) { // 每个 Module 必须有一个 release 环境配置
            String moduleName = moduleElement.getSimpleName().toString();
            throw new Exception(moduleName + " module must have a release environment configuration ( 每个 Module 必须有一个 release 环境配置 )");
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
    private static String _getEnvironmentVarName_UpperCase(
            final String moduleName,
            final String environmentName
    ) {
        return VAR_ENVIRONMENT_PREFIX + moduleName.toUpperCase() + "_" + environmentName.toUpperCase();
    }
}