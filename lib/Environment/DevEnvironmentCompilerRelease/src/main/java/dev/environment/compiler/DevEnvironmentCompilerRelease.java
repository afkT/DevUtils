package dev.environment.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
     * 获取该 Processor 能够处理的注解
     * @return 该 Processor 能够处理的注解
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Module.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 构建 DevEnvironment 类对象
        TypeSpec.Builder devEnvironmentClassBuilder = Utils.builderDevEnvironment_Class();
        // 创建 DevEnvironment 无参构造函数
        Utils.builderDevEnvironmentConstructor_Method(devEnvironmentClassBuilder);
        // 获取使用注解修饰的 Element
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Module.class);
        for (Element element : elements) {
            Element parent = element.getEnclosingElement();
            String parentName = parent.getSimpleName().toString();

        }
        // 创建 DevEnvironment JAVA 文件
        return Utils.createDevEnvironmentJavaFile(devEnvironmentClassBuilder, processingEnv);
    }
}
