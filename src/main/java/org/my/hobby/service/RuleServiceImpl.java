package org.my.hobby.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.my.hobby.core.Rule;
import org.my.hobby.repository.RuleRepository;

@Singleton
public class RuleServiceImpl implements RuleService {

    @Inject
    RuleRepository ruleRepository;

    @Override
    public Rule rule() {
        return ruleRepository.rule();
    }

    @Override
    public void update(Rule rule) {
        ruleRepository.deleteInsert(rule);
    }
}
