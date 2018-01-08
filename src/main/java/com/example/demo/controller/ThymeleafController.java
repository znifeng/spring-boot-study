package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {
    @RequestMapping("/test")
    public String index(ModelMap map){
        map.addAttribute("name", "zni.feng");
        return "index";
    }
}
