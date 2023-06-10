package com.ms.user.utils;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class UserUtils {

    static final Map<Integer, String> genderMap = ImmutableMap.of(
            0, "未知",
            1, "男",
            2, "女"
    );

    public static String convertGender(Integer key) {
        return genderMap.get(key);
    }
}
