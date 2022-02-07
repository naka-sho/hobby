package org.my.hobby.repository;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.my.hobby.core.Queue;
import org.my.hobby.core.Symbol;
import org.my.hobby.persistence.QueueMapper;
import org.my.hobby.persistence.QueueRecord;
import org.my.hobby.symbol.PayloadSendRequest;
import org.my.hobby.symbol.SymbolRequest;

@Singleton
public class CryptoRepositoryImpl implements CryptoRepository {

    @Inject
    @RestClient
    SymbolRequest symbolRequest;

    @Inject
    QueueMapper queueMapper;

    @Override
    public void send(Symbol symbol) {
        PayloadSendRequest payloadSendRequest = new PayloadSendRequest(
                symbol.sendAddress(),
                symbol.price(),
                symbol.message()
        );
        String send = symbolRequest.send(payloadSendRequest);
        Log.debug(send);
    }

    @Override
    public void add(Queue queue) {
        QueueRecord queueRecord = new QueueRecord()
                .setAddress(queue.address())
                .setTransaction(queue.transaction())
                .setUrl(queue.url());
        queueMapper.insert(queueRecord);
    }

    @Override
    public void delete(Long queueId) {
        queueMapper.delete(queueId);
    }
}
