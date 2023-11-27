package com.example.lispelDoc2.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LispelAddValueByUser {
    int number();
    String name();
    String name_hint();
    String name_title();
    int input_type();
    RepositoryEnum base() default RepositoryEnum.NO_BASE;
    Class repository() default Object.class;
    Class dao() default Object.class;
    String class_entity() default "";
    String repository_title() default "";
}
