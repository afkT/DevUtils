package dev.environment.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * detail: 模块 ( 注解标记类 )
 * @author Ttt
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Module {

    /**
     * 当前 {@link Module} 别名
     * @return {@link Module} 别名
     */
    String alias() default "";
}