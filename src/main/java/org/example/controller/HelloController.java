package org.example.controller;


import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.to.UserTO;
import org.example.service.UserService;
import org.example.util.OKHttpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

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

    @GetMapping("response/get")
    public String response(){
        HashMap<String, String> param = new HashMap<>();
        param.put("phone","15797704512");
        return OKHttpUtil.get("http://localhost:8082/api/user/read/getByPhone",param);
    }

    @GetMapping("response/post")
    public String responsePost(){
        HashMap<String, String> param = new HashMap<>();
        param.put("phone","15797704512");
        String json = JSON.toJSONString(param);
        return OKHttpUtil.postJson("http://localhost:8082/api/user/read/findByPhone",json);
    }
    @GetMapping("response/retrofit/post")
    public String responseRetrofitPost() throws IOException {
        return userService.responseRetrofitPost();
    }

    @GetMapping("response/retrofit/get")
    public String responseRetrofitGet() throws IOException {
        return userService.responseRetrofitGet();
    }
}
