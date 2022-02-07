package org.my.hobby.service;

import org.my.hobby.core.Queue;
import org.my.hobby.core.Symbol;

public interface CryptoService {
    void send(Symbol symbol);

    void add(Queue queue);
}
