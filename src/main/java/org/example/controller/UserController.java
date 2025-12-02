package org.example.controller;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.example.model.to.UserTO;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuyachang
 * @date 2025/12/2
 */
@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("save")
    public String helloWorld(){
        UserTO userTO = new UserTO();
        userService.saveUser(userTO);
        return JSON.toJSONString(userTO);
    }
}
