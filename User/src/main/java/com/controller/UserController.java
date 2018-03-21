package com.controller;

import com.hm.user.models.User;
import com.hm.user.service.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
        List<User> list= userRepository.findAll();
        for (User demo : list) {
            System.out.println(demo.toString());
        }
        return list;
    }

    @RequestMapping(value = "/user/find/{id}")
    public Optional<User> findOne(@PathVariable(name = "id") String id) {
        return userRepository.findById(new ObjectId(id));
    }

    @RequestMapping(value = "/user/findUsers")
    public Page<User> findUser() {
       Sort sort =  new Sort(Sort.Direction.ASC, "Id");
       return userRepository.findAll(new PageRequest(0, 10, sort));
    }

    public void UserLogin() {

    }

    public void UserLogout() {}


}
