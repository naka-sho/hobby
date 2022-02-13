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
            order by
                user_id
            limit 1 
            """)
    Optional<UserListRecord> user();

    @Select("""
            select
                users.*
            from
                users
            """)
    List<UserListRecord> all();

    @Insert("""
            INSERT INTO users
                (address)
            VALUES
                <foreach item = 'item' index = 'index' collection='usersList' open='(' separator=',' close=')'>
                    #{usersList.address}
                </foreach>
            """
    )
    void insert(@Param("users") UsersRecord usersRecord);

    @Delete("""
            <script>
                DELETE
                FROM
                    users
                WHERE
                    address in
                    <foreach item = 'item' index = 'index' collection='usersList' open='(' separator=',' close=')'>
                        #{usersList.address}
                    </foreach>
            </script>
            """)
    void delete(@Param("usersList") List<UsersRecord> usersRecordList);
}
