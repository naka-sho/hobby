package org.my.hobby.core;

import lombok.Getter;

public enum TransactionStatusGroupType {
    UNCONFIRMED("unconfirmed"),
    CONFIRMED("confirmed");

    @Getter
    private String name;

    TransactionStatusGroupType(String name){
        this.name = name;
    }

    static public boolean confirmed(String name){
        return TransactionStatusGroupType.CONFIRMED.getName().equals(name);
    }
}
