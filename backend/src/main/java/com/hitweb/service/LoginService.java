package com.hitweb.service;

import com.hitweb.entity.EmpLogin;
import com.hitweb.pojo.Result;

public interface LoginService {
    Result<String> login(EmpLogin empLogin);
}
