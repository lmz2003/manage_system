package com.hitweb.controller;

import com.hitweb.pojo.Result;
import com.hitweb.service.EmpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@CrossOrigin
@RequestMapping("/upload")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
public class UploadController {
    private final EmpService empService;

    @PostMapping
    public Result<String> upload(@RequestParam("image") MultipartFile file) throws IOException {
        log.info("upload, file:{}",file.getOriginalFilename());
        return empService.upload(file);
    }
}
