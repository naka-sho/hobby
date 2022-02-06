package org.my.hobby.repository;


import javax.inject.Inject;
import javax.inject.Singleton;

import java.security.cert.Extension;
import java.util.Set;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.my.hobby.core.Symbol;
import org.my.hobby.symbol.PayloadSendRequest;
import org.my.hobby.symbol.SymbolRequest;

@Singleton
public class CryptoRepositoryImpl implements CryptoRepository {

    @Inject
    @RestClient
    SymbolRequest symbolRequest;

    @Override
    public boolean send(Symbol symbol) {
        PayloadSendRequest payloadSendRequest = new PayloadSendRequest(
                symbol.sendAddress(),
                symbol.price(),
                symbol.message()
        );
        String send = symbolRequest.send(payloadSendRequest);
        return false;
    }
}
