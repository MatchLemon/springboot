package com.test1;

import com.hm.user.models.User;
import com.hm.user.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController

public class UserController {
    @Autowired
    public UserRepository userRepository;

    @RequestMapping(value = "/test")
    public String getStr(){
        return "qwe";
    }

    @RequestMapping(value = "/user/save")
    public User saveUser() {
        User user = userRepository.save(new User("jack", "123456", "095555555", "上海"));
        return user;
    }
    @RequestMapping(value = "/user/list")
    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
