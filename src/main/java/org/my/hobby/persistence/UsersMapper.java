package org.my.hobby.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapper {

    @Select("""
            select
                users.*,
                user_twitter.account as twitter_account
            from
                users
                    inner join user_twitter
                        on users.user_id = user_twitter.user_id
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
