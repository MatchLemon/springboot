package com.controller;

import com.hm.user.models.User;
import com.hm.user.service.UserRepository;
import com.hm.user.utils.Constant;
import com.hm.user.utils.Response;
import com.hm.user.utils.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public Page<User> findUser(@RequestParam(name = "page",defaultValue = "1") Integer page, @RequestParam(name = "size",defaultValue = "10") Integer size) {
       Sort sort =  new Sort(Sort.Direction.ASC, "Id");
       Page<User> userPage = userRepository.findAll(PageRequest.of(page-1, size, sort));
//       Map map = new HashMap();
//       map.put("items", userPage.getContent());
//       map.put("totalCount", userPage.getTotalElements());
//       map.put("totalPage", userPage.getTotalPages());
       return userPage;
    }

    @PostMapping(value = "/user/login")
    public Object UserLogin(@RequestBody Map<String,String> map, HttpSession session) {
        if (!map.containsKey("phone") || !map.containsKey("passwd") || StringUtils.isEmpty(map.get("phone")) || StringUtils.isEmpty(map.get("passwd"))) {
            return Response.fialed("params is requried");
        }
        User user = new User();
        user.setPhone(map.get("phone").toString());
        Example<User> example = Example.of(user, matching().withStringMatcher(StringMatcher.ENDING));
        Optional<User> option = userRepository.findOne(example);
        if(!option.isPresent()) {
            return Response.fialed(202,"user no exist");
        }
        user = option.get();
        session.setAttribute(Constant.SESSION_USER_KEY, user);
        return Response.success(user);
    }

    @PostMapping(value = "/user/logout")
    public Object UserLogout(HttpSession session) {
        session.invalidate();
        return Response.success("success");
    }
}
