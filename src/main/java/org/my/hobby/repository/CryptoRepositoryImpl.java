package org.my.hobby.repository;


import javax.inject.Singleton;

import org.my.hobby.core.Symbol;


@Singleton
public class CryptoRepositoryImpl implements CryptoRepository {
    @Override
    public boolean send(Symbol symbol) {
        return false;
    }
}
