package org.my.hobby.repository;

import org.my.hobby.core.Queue;
import org.my.hobby.core.Symbol;

public interface CryptoRepository {
    void send(Symbol symbol);
    void add(Queue queue);
    void delete(Long queueId);
}
