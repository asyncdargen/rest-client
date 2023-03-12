package ru.dargen.rest.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RequestParameters.class)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestParameter {

    String key();

    String value();

}
