package com.distribuido.chat_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {


    @GetMapping(value = "/{path:[^\\.]*}")
    @ResponseBody
    public String redirect() {
        return "index.html";
    }
}
