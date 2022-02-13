package org.my.hobby.repository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import java.util.Optional;

import org.my.hobby.core.CryptoType;
import org.my.hobby.core.NetworkType;
import org.my.hobby.core.Rule;
import org.my.hobby.persistence.RuleMapper;
import org.my.hobby.persistence.RuleRecord;

@Singleton
public class RuleRepositoryImpl implements RuleRepository {

    @Inject
    RuleMapper ruleMapper;

    @Override
    public Rule rule() {
        Optional<RuleRecord> rule = ruleMapper.rule();
        return rule.
                map(ruleRecord -> new Rule(
                        ruleRecord.getRuleId(),
                        CryptoType.cryptoTypeMap().getOrDefault(ruleRecord.getCryptoName(), CryptoType.SYMBOL),
                        ruleRecord.getHash(),
                        ruleRecord.getEpochAdjustment(),
                        ruleRecord.getPrivateKey(),
                        NetworkType.networkTypeMap().getOrDefault(ruleRecord.getNetworkType(), NetworkType.TEST),
                        ruleRecord.getMosaic(),
                        ruleRecord.getNode()
                ))
                .orElseThrow();
    }

    @Override
    @Transactional
    public void deleteInsert(Rule rule) {
        this.delete(rule.ruleId());
        this.insert(rule.ruleId(), rule);
    }

    @Override
    public void delete(Long ruleId) {
        ruleMapper.delete(ruleId);
    }

    @Override
    public void insert(Long ruleId, Rule rule) {
        RuleRecord ruleRecord = this.toRuleRecord(rule);
        if(ruleId == 0L){
            ruleMapper.insert(ruleRecord);
            return;
        }
        ruleMapper.insertPk(ruleId, ruleRecord);
        return;
    }

    @Override
    public void insert(Rule rule) {

        RuleRecord ruleRecord = this.toRuleRecord(rule);
        ruleMapper.insert(ruleRecord);
    }

    private RuleRecord toRuleRecord(Rule rule) {
        return new RuleRecord()
                .setCryptoName(rule.crypto().getName())
                .setHash(rule.hash())
                .setEpochAdjustment(rule.epochAdjustment())
                .setPrivateKey(rule.privateKey())
                .setNetworkType(rule.networkType().getType())
                .setMosaic(rule.mosaic())
                .setNode(rule.node());
    }
}
