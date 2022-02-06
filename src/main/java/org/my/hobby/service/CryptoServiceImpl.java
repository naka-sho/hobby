package org.my.hobby.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.my.hobby.core.Symbol;
import org.my.hobby.repository.CryptoRepository;

@Singleton
public class CryptoServiceImpl implements CryptoService {

    @Inject
    CryptoRepository cryptoRepository;

    @Override
    public boolean send(Symbol symbol) {
        return cryptoRepository.send(symbol);
    }
}
