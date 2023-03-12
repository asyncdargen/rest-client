package ru.dargen.rest.annotation.parameter;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {

    String value();

    boolean nullable() default true;

}
