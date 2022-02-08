package org.my.hobby.repository;

import java.util.List;

import org.my.hobby.core.Queue;
import org.my.hobby.core.Symbol;

public interface CryptoRepository {
    void send(Symbol symbol);
}
