package dev.environment.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * detail: 环境配置 ( 注解标记类 )
 * @author Ttt
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Environment {

    /**
     * 当前 {@link Environment} 具体配置值
     * @return {@link Environment} 具体配置值
     */
    String value();

    /**
     * 当前 {@link Environment} 别名
     * @return {@link Environment} 别名
     */
    String alias() default "";

    /**
     * 是否 Release 环境
     * <pre>
     *     同一个 {@link Module} 只能存在一个 Release 环境配置
     *     用于构建 Release 常量实体类
     * </pre>
     * @return {@code true} yes, {@code false} no
     */
    boolean isRelease() default false;
}