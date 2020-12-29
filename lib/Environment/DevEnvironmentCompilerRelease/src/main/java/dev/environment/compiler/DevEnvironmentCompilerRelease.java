package dev.environment.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import dev.environment.annotation.Module;

/**
 * detail: DevEnvironment Release 编译生成类
 * @author Ttt
 */
@AutoService(Processor.class)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@SupportedAnnotationTypes("dev.environment.annotation.Module")
public class DevEnvironmentCompilerRelease
        extends AbstractProcessor {

    /**
     * Processor 支持的最新的源版本
     * @return {@link SourceVersion}
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    /**
     * 获取该 Processor 能够处理的注解
     * @return 该 Processor 能够处理的注解
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Module.class.getCanonicalName());
    }

    @Override
    public boolean process(
            Set<? extends TypeElement> set,
            RoundEnvironment roundEnvironment
    ) {
        // 构建 DevEnvironment 类对象
        TypeSpec.Builder devEnvironmentClassBuilder = Utils.builderDevEnvironment_Class();
        // 获取使用注解修饰的 Module Element
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Module.class);
        for (Element element : elements) {
            try {
                // 创建 Module 数据
                Utils.builderModule_DATA(devEnvironmentClassBuilder, element, processingEnv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 构建 static{} 初始化代码
        Utils.builderStaticInit(devEnvironmentClassBuilder);
        // 构建 isRelease 方法
        Utils.builderIsReleaseMethod(devEnvironmentClassBuilder);
        // 构建 getXxx 方法代码
        Utils.builderGetMethod(devEnvironmentClassBuilder);
        // 构建模块环境改变触发事件方法
        Utils.builderEnvironmentChangeListener(devEnvironmentClassBuilder);
        // 构建存储相关方法
        Utils.builderStorageMethod(devEnvironmentClassBuilder);
        // 构建 Reset 方法
        Utils.builderResetMethod(devEnvironmentClassBuilder);
        // 创建 DevEnvironment JAVA 文件
        return Utils.createDevEnvironmentJavaFile(devEnvironmentClassBuilder, processingEnv);
    }
}