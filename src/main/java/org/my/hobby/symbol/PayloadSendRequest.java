package org.my.hobby.symbol;

public record PayloadSendRequest(
        String sendAddress,
        Long price,
        String message
) {

}

