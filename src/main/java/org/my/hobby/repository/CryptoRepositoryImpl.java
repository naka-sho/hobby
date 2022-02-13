package org.my.hobby.repository;


import javax.inject.Inject;
import javax.inject.Singleton;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.my.hobby.core.Rule;
import org.my.hobby.core.Symbol;
import org.my.hobby.symbol.PayloadSendRequest;
import org.my.hobby.symbol.SymbolRequest;

@Singleton
public class CryptoRepositoryImpl implements CryptoRepository {

    @Inject
    @RestClient
    SymbolRequest symbolRequest;

    @ConfigProperty(name = "chat.server.url.add.log")
    String urlAddLog;

    @ConfigProperty(name = "chat.server.url.delete.error.address")
    String urlDeleteErrorAddress;

    @Override
    public void send(Rule rule, Symbol symbol) {
        PayloadSendRequest payloadSendRequest = new PayloadSendRequest(
                symbol.sendAddress(),
                symbol.price(),
                symbol.message(),
                rule.hash(),
                rule.epochAdjustment(),
                rule.privateKey(),
                rule.networkType().getType(),
                rule.mosaic(),
                rule.node(),
                urlAddLog,
                urlDeleteErrorAddress
        );
        String send = symbolRequest.send(payloadSendRequest);
        Log.debug(send);
    }
}
