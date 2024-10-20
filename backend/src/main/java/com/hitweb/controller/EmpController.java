package com.hitweb.controller;

import com.hitweb.pojo.Gender;
import com.hitweb.pojo.EmpJob;
import com.hitweb.pojo.PageResult;
import com.hitweb.entity.EmpUpdate;
import com.hitweb.pojo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitweb.entity.EmpCreate;
import com.hitweb.pojo.Emp;
import com.hitweb.service.EmpService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@CrossOrigin
@RequestMapping("/emps")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
public class EmpController {
    private final EmpService empService;

    @GetMapping("/all")
    public Result<List<Emp>> getAll()
    {
        log.info("getAll");
        return empService.list();
    }

    @GetMapping
    public Result<PageResult<Emp>> getEmp(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) Short gender,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                     @RequestParam(required = false,defaultValue = "1") Integer page,
                                     @RequestParam(required = false,defaultValue = "10") Integer pageSize)
    {
        log.info("name:{},gender:{},begin:{},end:{},page:{},pageSize:{}",name,gender,begin,end,page,pageSize);
        return empService.getEmpPage(name,gender,begin,end,page,pageSize);
    }

    @DeleteMapping("/{ids}")
    public Result<Void> delete(@PathVariable List<Integer> ids)
    {
        log.info("delete, ids:{}",ids);
        return empService.delete(ids);
    }

    @PostMapping
    public Result<Void> save(@Validated @RequestBody EmpCreate emp)
    {
        log.info("save, emp:{}",emp);
        return empService.save(emp);
    }

    @GetMapping("/{id}")
    public Result<Emp> getById(@PathVariable Integer id){
        log.info("getById, id:{}",id);
        return empService.getById(id);
    }

    @PutMapping
    public Result<Void> update(@Validated @RequestBody EmpUpdate empUpdate){
        log.info("update, emp:{}", empUpdate);
        return empService.update(empUpdate);
    }

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("image") MultipartFile file) throws IOException {
        log.info("upload, file:{}",file.getOriginalFilename());
        return empService.upload(file);
    }

    @GetMapping("/gender")
    public Result<List<Gender>> getGender()
    {
        log.info("getGender");
        return empService.getGender();
    }

    @GetMapping("/job")
    public Result<List<EmpJob>> getJob()
    {
        log.info("getJob");
        return empService.getJob();
    }

    @GetMapping("/teacher")
    public Result<List<Emp>> getTeacher()
    {
        log.info("getTeacher");
        return empService.getTeacher();
    }


}
