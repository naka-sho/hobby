package org.my.hobby.core;

public record Queue(
        Long queueId,
        String address,
        String transaction,
        Long price,
        String url
) {
    public Queue(String address,
                 String transaction,
                 Long price,
                 String url) {
        this(
                0L,
                address,
                transaction,
                price,
                url
        );
    }
}
