package org.my.hobby.repository;


import javax.inject.Singleton;


@Singleton
public class CryptoRepositoryImpl implements CryptoRepository {

    private final String baseUrl = "http://ngl-dual-101.testnet.symboldev.network:3000";

    @Override
    public boolean send(Symbol symbol) {
        return false;
    }
}
