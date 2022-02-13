package org.my.hobby.persistence;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapper {

    @Select("""
            select
                users.*
            from
                users
                left join send_log on
                    users.address = send_log.address
            where
                send_log.send_log_id is null
            order by
                user_id
            limit 1 
            """)
    Optional<UserListRecord> user();

    @Select("""
            select
                users.address,
                send_log.send_log_id
            from
                users
                left join send_log on
                    users.address = send_log.address
            """)
    List<UserSendListRecord> userSendList();

    @Insert("""
            <script>
                INSERT INTO users
                    (address)
                VALUES
                    <foreach item = 'item' index = 'index' collection='usersList' open='' separator=',' close=''>
                        (#{item.address})
                    </foreach>
            </script>
            """
    )
    void insert(@Param("usersList") List<UsersRecord> usersRecord);

    @Delete("""
            <script>
                DELETE
                FROM
                    users
                WHERE
                    address in
                    <foreach item = 'item' index = 'index' collection='usersList' open='(' separator=',' close=')'>
                        #{item.address}
                    </foreach>
            </script>
            """)
    void delete(@Param("usersList") List<UsersRecord> usersRecord);

    @Delete("""
                DELETE
                FROM
                    users
                WHERE
                    address = #{address}
            """)
    void deleteByAddress(@Param("address") String address);
}
