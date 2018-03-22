package com.controller;

import com.hm.user.models.User;
import com.hm.user.service.UserRepository;
import com.hm.user.utils.Response;
import com.hm.user.utils.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.ExampleMatcher.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.matching;

@RestController

public class UserController {
    @Autowired
    public UserRepository userRepository;

    @RequestMapping(value = "/test")
    public String getStr(){
        return "qwe";
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public Object saveUser(@RequestBody @Valid User user, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return Response.fialed(validResult.getAllErrors());
        }
        if (user == null) {
            return Response.fialed("user is null");
        }
        User result = userRepository.save(user);
        return result;
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
       return userRepository.findAll(PageRequest.of(0,10,sort));
    }
    @PostMapping(value = "/user/login")
    public Object UserLogin(@RequestBody Map<String,String> map) {
        if (!map.containsKey("phone") || !map.containsKey("passwd") || StringUtils.isEmpty(map.get("phone")) || StringUtils.isEmpty(map.get("passwd"))) {
            return Response.fialed("params is requried");
        }
        User user = new User();
        user.setPhone(map.get("phone").toString());
        Example<User> example = Example.of(user, matching().withStringMatcher(StringMatcher.ENDING));
        Optional<User> option = userRepository.findOne(example);
        if(option.isPresent()) {
            return Response.fialed(202,"user no exist");
        }

        return Response.success(user);
    }

    public void UserLogout() {}


}
