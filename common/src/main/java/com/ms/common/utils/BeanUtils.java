package com.ms.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

    public static <T, E> List<E> copyList(List<T> sourceList, Class<E> clazz) {
        List<E> desList = new ArrayList<>();
        for (T source: sourceList) {
            try {
                E obj = clazz.getDeclaredConstructor().newInstance();
                org.springframework.beans.BeanUtils.copyProperties(source, obj);
                desList.add(obj);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return desList;
    }
}
