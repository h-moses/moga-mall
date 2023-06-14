package com.ms.user.utils;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class UserUtils {

    static final Map<Integer, String> genderMap = ImmutableMap.of(
            0, "未知",
            1, "男",
            2, "女"
    );

    public static String convert2String(Integer key) {
        return genderMap.get(key);
    }
    public static Integer convert2Code(String value) {
        for (Map.Entry<Integer, String> entry: genderMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return 0;
    }
}
