package org.my.hobby.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * 使用できる通貨を設定する
 */
public enum CryptoType {
    SYMBOL("symbol", "XYM"),
    // BTOCOIN("BTOCOIN", "BTC"),
    ;

    @Getter
    private String name;

    @Getter
    private String shortName;

    CryptoType(String name, String shortName){
        this.name = name;
        this.shortName = shortName;
    }

    private static List<CryptoType> cryptoTypeList = List.of(CryptoType.values());

    private static Map<String, CryptoType> cryptoTypeMap = Arrays.stream(CryptoType.values()).collect(
            Collectors.toMap(
                    e -> e.name,
                    e -> e
            )
    );

    public static List<CryptoType> cryptoTypes(){
        return cryptoTypeList;
    }

    public static Map<String, CryptoType> cryptoTypeMap(){
        return cryptoTypeMap;
    }
}
