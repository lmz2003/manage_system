package com.hitweb.service.impl;

import com.hitweb.entity.EmpLogin;
import com.hitweb.pojo.Emp;
import com.hitweb.pojo.Result;
import com.hitweb.utils.JwtProperties;
import com.hitweb.repository.EmpRepository;
import com.hitweb.service.LoginService;
import com.hitweb.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.hitweb.message.Message.USERNAME_PASSWORD_ERROR;
import static com.hitweb.message.Message.USER_ID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginServiceImpl implements LoginService {
    private final EmpRepository empRepository;
    private final JwtProperties jwtProperties;

    @Override
    public Result<String> login(EmpLogin empLogin) {
        String username = empLogin.getUsername();
        String password = empLogin.getPassword();
        if (empRepository.findByUsernameAndPassword(username, password) == null) {
            return Result.error(USERNAME_PASSWORD_ERROR);
        }
        Emp emp = empRepository.findByUsernameAndPassword(username, password);
        Map<String,Object> claims = new HashMap<>();
        claims.put(USER_ID,emp.getId());
        String jwt = JwtUtils.generateJwt(jwtProperties.getSecret(), jwtProperties.getExpire(), claims);
        return Result.success(jwt);
    }
}
