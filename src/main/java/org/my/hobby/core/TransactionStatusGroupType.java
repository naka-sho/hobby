package org.my.hobby.core;

import lombok.Getter;

public enum TransactionStatusGroupType {
    UNCONFIRMED("unconfirmed"),
    FAILED("failed"),
    CONFIRMED("confirmed");

    @Getter
    private String name;

    TransactionStatusGroupType(String name){
        this.name = name;
    }

    static public boolean unconfirmed(String name){
        return TransactionStatusGroupType.UNCONFIRMED.getName().equals(name);
    }

    static public boolean failed(String name){
        return TransactionStatusGroupType.FAILED.getName().equals(name);
    }
}
