package org.my.hobby.core;

import lombok.Data;

@Data
public class TransactionStatus {
    private String group;
    private String code;
    private String hash;
    private Long deadline;
    private Long height;
}
