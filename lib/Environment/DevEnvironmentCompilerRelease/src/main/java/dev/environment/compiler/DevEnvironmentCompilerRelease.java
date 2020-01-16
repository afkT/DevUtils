package dev.environment.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import dev.environment.annotation.Module;

/**
 * detail: DevEnvironment Release 编译生成类
 * @author Ttt
 */
@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@SupportedAnnotationTypes("dev.environment.annotation.Module")
public class DevEnvironmentCompilerRelease extends AbstractProcessor {

    /**
     * Processor 支持的最新的源版本
     * @return {@link SourceVersion}
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    /**
     * 返回该 Processor 能够处理的注解
     * @return 该 Processor 能够处理的注解
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Module.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        // 获取使用注解修饰节点
//        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Module.class);

        MethodSpec main = MethodSpec.methodBuilder("main")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(void.class)
            .addParameter(String[].class, "args")
            .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
            .build();

        // 构建 DevEnvironment 类对象
        TypeSpec.Builder devEnvironmentClassBuilder = builderDevEnvironmentClass();


        devEnvironmentClassBuilder.addMethod(main);

//        HashMap<String, HashSet<Element>> nameMap = new HashMap<>();
//        // 遍历处理带注解的 Element, 把他们分类保存在 Map 中, key = 包裹类, value = 类中所有使用注解的 Element
//        for (Element element : elements) {
//            Element parent = element.getEnclosingElement();
//            String parentName = parent.getSimpleName().toString();
//            HashSet<Element> set111111 = nameMap.get(parentName);
//            if (set111111 == null) {
//                set111111 = new HashSet<>();
//            }
//            set111111.add(element);
//            nameMap.put(parentName, set111111);
//        }

        // 创建 DevEnvironment JAVA 文件
        return createDevEnvironmentJavaFile(devEnvironmentClassBuilder);

//        try {
//            Filer filer = processingEnv.getFiler();
//            JavaFileObject serviceRegistry = filer.createSourceFile("DDDDDDDonfig");
//            Writer writer = serviceRegistry.openWriter();
//            writer.write("public class Config {");
//            writer.write("}");
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return true;
    }

    // ====================================
    // = 内部方法 ( debug、release 共用 ) =
    // ====================================

    /**
     * 构建 DevEnvironment 类对象
     * @return {@link TypeSpec.Builder}
     */
    protected TypeSpec.Builder builderDevEnvironmentClass() {
        // 创建 DevEnvironment 类
        return TypeSpec.classBuilder(Constants.ENVIRONMENT_FILE_NAME)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }

    /**
     * 创建 DevEnvironment JAVA 文件
     * @param builder DevEnvironment 构建类对象
     * @return {@code true} success, {@code false} fail
     */
    protected boolean createDevEnvironmentJavaFile(TypeSpec.Builder builder) {
        JavaFile javaFile = JavaFile.builder(Constants.PACKAGE_NAME, builder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
