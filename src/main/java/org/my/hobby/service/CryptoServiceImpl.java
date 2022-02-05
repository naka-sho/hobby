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
    public boolean send() {
        return cryptoRepository.send(new Symbol("NBSNDMDP5C3AUWGU4GG5PPSHRZGE3IEMQNOPPRY"));
    }
}
