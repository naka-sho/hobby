package org.my.hobby.service;

import java.util.List;

import org.my.hobby.core.Queue;
import org.my.hobby.core.Rule;
import org.my.hobby.core.Symbol;

public interface CryptoService {
    void send(Rule rule, Symbol symbol);

    void add(Queue queue);

    List<Queue> allQueue();

    void deleteQueue(Long queueId);

    void createLog(Queue queue);
}
