package com.test1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
public class WebController {

    @RequestMapping(value = "/index")
    public String findIndex(HashMap<String,Object> map) {
        map.put("name", "Jack");
        map.put("say", "hello!!!");
        return "index";
    }
}
