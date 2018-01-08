package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.domain.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MybatisTestController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/testinsert")
    public User testdb() throws  Exception{
        userMapper.insert("zni", 27);
        User user = userMapper.findByName("zni");
        return user;
    }

    @RequestMapping("/queryall")
    public User testQueryAll() throws  Exception{
        List<User> users = userMapper.findAll();
        return users.get(0);
    }

    @RequestMapping("/testupdate")
    public User testUpdate() throws  Exception{
        userMapper.updateName("znifeng", "zni");
        User user = userMapper.findByName("znifeng");
        return user;
    }

    @RequestMapping("/testdelete")
    public User testDelete() throws  Exception{
        User user = userMapper.findByName("cy");
        userMapper.delete(2L);
        return user;
    }


}
