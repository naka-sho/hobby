package org.my.hobby.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.my.hobby.core.Queue;

@Mapper
public interface QueueMapper {

    @Select("""
            select * from queue order by queue_id
            """)
    List<Queue> all();

    @Insert("""
            INSERT INTO queue
                (address,
                transaction,
                url)
            VALUES
                (#{queue.address},
                #{queue.transaction},
                #{queue.url})
            """)
    void insert(@Param("queue") QueueRecord queueRecord);

    @Delete("""
            delete from queue where queue = #{queueId}
            """)
    void delete(@Param("queueId") Long queueId);
}
