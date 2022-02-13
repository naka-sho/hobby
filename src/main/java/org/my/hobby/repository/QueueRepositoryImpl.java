package org.my.hobby.repository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

import io.quarkus.logging.Log;
import org.my.hobby.core.Queue;
import org.my.hobby.persistence.QueueMapper;
import org.my.hobby.persistence.QueueRecord;
import org.my.hobby.persistence.SendLogMapper;
import org.my.hobby.persistence.SendLogRecord;

@Singleton
public class QueueRepositoryImpl implements QueueRepository
{
    @Inject
    QueueMapper queueMapper;

    @Inject
    SendLogMapper sendLogMapper;

    @Override
    public void add(Queue queue) {
        QueueRecord queueRecord = new QueueRecord()
                .setAddress(queue.address())
                .setTransaction(queue.transaction())
                .setUrl(queue.url());
        queueMapper.insert(queueRecord);
    }

    @Override
    public List<Queue> all() {
        List<QueueRecord> all = queueMapper.all();
        List<Queue> collect = all.stream().map(e ->
                        new Queue(e.getQueueId(),
                                e.getAddress(),
                                e.getTransaction(),
                                e.getUrl()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void delete(Long queueId) {
        Log.debug("削除開始");
        queueMapper.delete(queueId);
    }

    @Override
    public void createLog(Queue queue) {
        SendLogRecord sendLogRecord = new SendLogRecord()
                .setAddress(queue.address())
                .setTransaction(queue.transaction())
                .setUrl(queue.url());
        sendLogMapper.insert(sendLogRecord);
    }
}
