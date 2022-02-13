package org.my.hobby.symbol;

public record PayloadSendRequest(
        String sendAddress,
        Long price,
        String message,
        String hash,
        String epochAdjustment,
        String privateKey,
        String networkType,
        String mosaic,
        String node,
        String urlAddLog,
        String urlDeleteErrorAddress
) {

}

