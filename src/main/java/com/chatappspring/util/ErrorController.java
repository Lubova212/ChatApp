package com.chatappspring.util;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ErrorController {

    @PostMapping("/error")
    public String handleError(){
        return "errorPage";
    }
}
