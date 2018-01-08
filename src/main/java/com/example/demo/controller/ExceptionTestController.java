package com.example.demo.controller;

import com.example.demo.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionTestController {
    @RequestMapping("/test1")
    public String test1() throws  Exception{
        throw new Exception("发生错误");
    }

    @RequestMapping("/test2")
    public  String test2() throws MyException{
        throw new MyException("发生错误2");
    }
}
