package com.example.demo.controller;

import com.example.demo.annotation.NotAllowEmpty;
import com.example.demo.annotation.NotNull;
import com.example.demo.service.FirstService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private Logger logger = LogManager.getLogger(HelloController.class);
    @Autowired
    private FirstService fs;
    @RequestMapping("/hello1")
    public String hello1(@NotAllowEmpty  @NotNull String label,   @NotNull  String version){
        logger.warn("label: " + label);
        logger.warn("version: " + version);
        return label+"-"+version;
    }

    @RequestMapping("/hello2")
    public String hello2(){
        String msg = fs.getValidEmail();
        logger.error("this is hello2");
        return msg;
    }

    @RequestMapping("/hello3")
    public String hello3(){
        String msg = fs.getValue();
        logger.info("this is hello3");
        return msg;
    }

    @RequestMapping("/hello4")
    public Long hello4(){
        Long msg = fs.getBignumber();
        return msg;
    }

    @RequestMapping("/hello5")
    public Integer hello5(){
        Integer msg = fs.getNumber();
        return msg;
    }

    @RequestMapping("/hello6")
    public Integer hello6(){
        Integer msg = fs.getTest1();
        return msg;
    }

    @RequestMapping("/hello7")
    public Integer hello7(){
        Integer msg = fs.getTest2();
        return msg;
    }
}
