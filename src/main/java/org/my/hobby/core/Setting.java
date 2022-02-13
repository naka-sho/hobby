package org.my.hobby.core;

public record Setting(
        CryptoType crypto,
        String hash,
        String epochAdjustment,
        String privateKey,
        NetworkType networkType,
        String mosaic,
        String node
) {
    /**
     * デフォルト値設定
     */
    Setting() {
        this(
                CryptoType.SYMBOL,
                "7FCCD304802016BEBBCD342A332F91FF1F3BB5E902988B352697BE245F48E836",
                "1573430400",
                "54CB7AA88F46CB140D3B9341835DAF61F6F4B1EC93D6270BE53C53B769072487",
                NetworkType.TEST,
                "3A8416DB2D53B6C8",
                "https://sym-test.opening-line.jp:3001"
        );
    }
}
