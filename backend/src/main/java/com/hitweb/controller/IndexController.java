package com.hitweb.controller;

import com.hitweb.pojo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RequestMapping("/index")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
public class IndexController {
    @GetMapping
    public Result<String> index() {
        return Result.success("index");
    }
}
