package com.hitweb.controller;

import com.hitweb.entity.DeptCreate;
import com.hitweb.entity.DeptUpdate;
import com.hitweb.pojo.Dept;
import com.hitweb.pojo.Result;
import com.hitweb.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
@CrossOrigin
public class DeptController {
    private final DeptService deptService;

    @GetMapping
    public Result<List<Dept>> getDept(){
        log.info("查询部门");
        return deptService.list();
    }

    @DeleteMapping("/{ids}")
    public Result<String> delete(@PathVariable List<Integer> ids){
        log.info("删除部门");
        return deptService.delete(ids);
    }
    
    @PostMapping
    public Result<String> add(@RequestBody DeptCreate name){
        return deptService.add(name);
    }

    @GetMapping("/{id}")
    public Result<Dept> getById(@PathVariable Integer id){
        return deptService.getById(id);
    }

    @PutMapping
    public Result<String> update(@RequestBody DeptUpdate deptUpdate){
        log.info("修改部门,id:{},name:{}", deptUpdate.getId(), deptUpdate.getName());
        return deptService.update(deptUpdate.getId(), deptUpdate.getName());
    }
}
