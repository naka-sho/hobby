package org.my.hobby.repository;

import javax.inject.Singleton;
import java.util.List;

import org.my.hobby.core.Queue;

public interface QueueRepository {
    void add(Queue queue);
    List<Queue> all();
    void delete(Long queueId);
    void createLog(Queue queue);
}
