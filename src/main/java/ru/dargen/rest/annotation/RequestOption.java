package ru.dargen.rest.annotation;

import java.lang.annotation.*;

@Repeatable(RequestOptions.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestOption {

    String option();

    String value();

}
