package org.my.hobby.core;

public record Queue(
        Long queueId,
        String address,
        String transaction,
        String url
) {
    public Queue(String address,
          String transaction,
          String url){
        this(
                0L,
                address,
                transaction,
                url
        );
    }
}
