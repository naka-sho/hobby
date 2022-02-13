package org.my.hobby.persistence;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;
import org.my.hobby.core.CryptoType;
import org.my.hobby.core.NetworkType;

@Data
@Accessors(chain = true)
public class RuleRecord {
    private Long ruleId;
    private String cryptoName;
    private String hash;
    private String epochAdjustment;
    private String privateKey;
    private String networkType;
    private String mosaic;
    private String node;
    private LocalDateTime createdAt;
}
