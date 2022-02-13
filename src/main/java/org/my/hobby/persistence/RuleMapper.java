package org.my.hobby.persistence;

import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RuleMapper {
    @Select("""
        SELECT
            rule_id,
            crypto_name,
            hash,
            epoch_adjustment,
            private_key,
            network_type,
            mosaic,
            node,
            created_at
        FROM
            rule
    """)
    Optional<RuleRecord> rule();

    @Delete("""
            delete from
                rule
            where
                rule_id = #{rule_id}
            """)
    void delete(@Param("rule_id") Long ruleId);

    @Insert("""
            INSERT INTO rule
                (rule_id,
                crypto_name,
                hash,
                epoch_adjustment,
                private_key,
                network_type,
                mosaic,
                node)
            VALUES
                (
                #{rule_id},
                #{rule.cryptoName},
                #{rule.hash},
                #{rule.epochAdjustment},
                #{rule.privateKey},
                #{rule.networkType},
                #{rule.mosaic},
                #{rule.node}
                )
            """)
    void insertPk(@Param("rule_id") Long ruleId,
                @Param("rule") RuleRecord rule);

    @Insert("""
            INSERT INTO rule
                (crypto_name,
                hash,
                epoch_adjustment,
                private_key,
                network_type,
                mosaic,
                node)
            VALUES
                (
                #{rule.cryptoName},
                #{rule.hash},
                #{rule.epochAdjustment},
                #{rule.privateKey},
                #{rule.networkType},
                #{rule.mosaic},
                #{rule.node}
                )
            """)
    void insert(@Param("rule") RuleRecord rule);
}
