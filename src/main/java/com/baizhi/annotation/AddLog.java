package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.zip.Deflater;

//注解类
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AddLog {
    String name() default "";
    String value();
}
