package org.my.hobby.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.my.hobby.core.Queue;
import org.my.hobby.core.Symbol;
import org.my.hobby.repository.CryptoRepository;

@Singleton
public class CryptoServiceImpl implements CryptoService {

    @Inject
    CryptoRepository cryptoRepository;

    @Override
    public void send(Symbol symbol) {
        cryptoRepository.send(symbol);
    }

    @Override
    public void add(Queue queue) {
        cryptoRepository.add(queue);
    }
}
