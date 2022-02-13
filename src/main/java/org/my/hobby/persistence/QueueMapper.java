package org.my.hobby.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QueueMapper {

    @Select("""
            select * from queue order by queue_id
            """)
    List<QueueRecord> all();

    @Insert("""
            INSERT INTO queue
                (address,
                transaction,
                price,
                url)
            VALUES
                (#{queue.address},
                #{queue.transaction},
                #{queue.price},
                #{queue.url})
            """)
    void insert(@Param("queue") QueueRecord queueRecord);

    @Delete("""
            delete from queue where queue_id = #{queueId}
            """)
    void delete(@Param("queueId") Long queueId);
}
