package org.my.hobby.controller.crypto.currency;

import org.my.hobby.core.CryptoType;
import org.my.hobby.core.NetworkType;

public record ViewSetting(
        CryptoType crypto,
        String hash,
        String epochAdjustment,
        String privateKey,
        NetworkType networkType,
        String mosaic,
        String node
) {
}
