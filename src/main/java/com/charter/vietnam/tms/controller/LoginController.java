package com.charter.vietnam.tms.controller;

import com.charter.vietnam.tms.layout.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    @Layout("layout/blank")
    public String login(){
        return "login";
    }

    @GetMapping("/index")
    @Layout("layout/default")
    public String index() {
        return "index";
    }
}
