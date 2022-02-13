package org.my.hobby.repository;

import org.my.hobby.core.Rule;
import org.my.hobby.core.Symbol;

public interface CryptoRepository {
    void send(Rule rule, Symbol symbol);
}
