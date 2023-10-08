package com.example.lispeldoc2.interfaces;

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
}
