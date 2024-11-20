package ru.dargen.rest.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ReflectUtil {

    public List<Class<?>> scanInterfaces(Class<?> clazz, List<Class<?>> interfaces) {
        interfaces.add(clazz);
        for (Class<?> interfaceClass : clazz.getInterfaces()) {
            scanInterfaces(interfaceClass, interfaces);
        }
        return interfaces;
    }

    public List<Class<?>> scanInterfaces(Class<?> clazz) {
        return scanInterfaces(clazz, new ArrayList<>());
    }

}
