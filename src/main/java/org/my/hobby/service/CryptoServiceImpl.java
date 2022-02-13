package org.my.hobby.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;

import org.my.hobby.core.Queue;
import org.my.hobby.core.Symbol;
import org.my.hobby.repository.CryptoRepository;
import org.my.hobby.repository.QueueRepository;

@Singleton
public class CryptoServiceImpl implements CryptoService {

    @Inject
    CryptoRepository cryptoRepository;

    @Inject
    QueueRepository queueRepository;

    @Override
    public void send(Symbol symbol) {
        cryptoRepository.send(symbol);
    }

    @Override
    public void add(Queue queue) {
        queueRepository.add(queue);
    }

    @Override
    public List<Queue> allQueue() {
        return queueRepository.all();
    }

    @Override
    public void deleteQueue(Long queueId) {
        queueRepository.delete(queueId);
    }
}
