package ru.dargen.rest.annotation;

import java.lang.annotation.*;

@Repeatable(RequestHeaders.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestHeader {

    String key();

    String value();

}
