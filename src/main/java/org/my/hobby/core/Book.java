package org.my.hobby.core;

import io.netty.util.internal.StringUtil;

public record Book(String title, String author) {
    public boolean isFind(){
        return !StringUtil.isNullOrEmpty(title);
    }

    public String message(){
        if(isFind()){
            return "";
        };
        return "not found book";
    }
}
