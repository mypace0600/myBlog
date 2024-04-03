package com.project.myBlog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main() {

        return "index";
    }
    @GetMapping("/lop")
    public String post() {

        return "index";
    }
}
