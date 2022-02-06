package org.my.hobby.repository;

import org.my.hobby.core.Rule;

public interface RuleRepository {
    Rule rule();
    void deleteInsert(Rule rule);
    void delete(Long ruleId);
    void insert(Long ruleId, Rule rule);
    void insert(Rule rule);
}
