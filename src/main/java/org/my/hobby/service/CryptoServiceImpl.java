package org.my.hobby.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.List;

import org.my.hobby.core.Queue;
import org.my.hobby.core.Rule;
import org.my.hobby.core.Symbol;
import org.my.hobby.repository.CryptoRepository;
import org.my.hobby.repository.QueueRepository;
import org.my.hobby.repository.UserRepository;

@Singleton
public class CryptoServiceImpl implements CryptoService {

    @Inject
    CryptoRepository cryptoRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    QueueRepository queueRepository;

    @Override
    public String address() {
        return userRepository.address();
    }

    @Override
    public void send(Rule rule, Symbol symbol) {
        cryptoRepository.send(rule, symbol);
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

    @Override
    public void createLog(Queue queue) {
        queueRepository.createLog(queue);
    }
}
