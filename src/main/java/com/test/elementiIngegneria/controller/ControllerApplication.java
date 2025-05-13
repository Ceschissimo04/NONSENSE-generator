package com.test.elementiIngegneria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerApplication {

    @GetMapping("/")
    public String Controller(){
        return "index";
    }
    
}
