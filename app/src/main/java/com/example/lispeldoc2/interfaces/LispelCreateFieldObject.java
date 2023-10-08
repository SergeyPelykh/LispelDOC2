package com.example.lispeldoc2.interfaces;

import com.example.lispeldoc2.repository.FieldRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.example.lispeldoc2.repository.FieldRepository;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LispelCreateFieldObject {
    Class repository_class();
}
