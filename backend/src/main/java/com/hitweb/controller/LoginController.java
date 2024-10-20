package com.hitweb.controller;

import com.hitweb.entity.EmpLogin;
import com.hitweb.pojo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.hitweb.service.LoginService;

@Slf4j
@CrossOrigin
@RequestMapping
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody EmpLogin empLogin){
        log.info("用户名:{},密码:{}", empLogin.getUsername(), empLogin.getPassword());
        return loginService.login(empLogin);
    }
}
