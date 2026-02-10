package org.example.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.to.UserTO;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
