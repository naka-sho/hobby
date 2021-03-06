package org.my.hobby.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SendLogMapper {
    @Insert("""
            INSERT INTO send_log
                (address,
                transaction,
                price,
                url)
            VALUES
                (#{sendLog.address},
                #{sendLog.transaction},
                #{sendLog.price},
                #{sendLog.url})
    """)
    void insert(@Param("sendLog") SendLogRecord sendLogRecord);
}
