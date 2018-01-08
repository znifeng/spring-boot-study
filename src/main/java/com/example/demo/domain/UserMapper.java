package com.example.demo.domain;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    @Select("SELECT * FROM user WHERE NAME =#{name}")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO user(NAME, AGE) VALUES(#{name}, #{age})")
    int insert(@Param("name")String name, @Param("age") Integer age);

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Update("UPDATE user SET NAME=#{newName} WHERE NAME=#{oldName}")
    void updateName(@Param("newName")String newName, @Param("oldName") String oldName);

    @Delete("DELETE FROM user WHERE id=#{id}")
    void delete(Long id);
}
