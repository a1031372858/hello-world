package org.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.po.UserPO;
import org.example.model.request.UserPageRequest;
import org.example.model.to.UserTO;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("hello")
public class HelloController {

    private final UserService userService;

    @GetMapping("index")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("user/get")
    public UserTO userInfo(){
        return userService.userInfo();
    }

    @GetMapping("user/page")
    public Page<UserTO> userPage(UserPageRequest request){
        if(Objects.isNull(request)||Objects.isNull(request.getPageNum())||Objects.isNull(request.getSize())){
            return new Page<>();
        }
        Page<UserPO> page = new Page<>();
        page.setCurrent(request.getPageNum());
        page.setSize(request.getSize());
        return userService.selectPage(page);
    }
}
