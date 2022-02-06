package org.my.hobby.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;

public enum NetworkType {
    TEST("test", true),
    PROD("prod", false);

    @Getter
    private String type;

    @Getter
    private boolean defaultChecked;

    NetworkType(String type, boolean defaultChecked) {
        this.type = type;
        this.defaultChecked = defaultChecked;
    }

    private static List<NetworkType> networkTypeList = List.of(NetworkType.values());

    private static Map<String ,NetworkType> networkTypeMap = Arrays.stream(NetworkType.values())
            .collect(Collectors.toMap(
                    e ->e.type,
                    e -> e
            ));

    public static List<NetworkType> networkTypeList() {
        return networkTypeList;
    }

    public static Map<String ,NetworkType> networkTypeMap() {
        return networkTypeMap;
    }
}
