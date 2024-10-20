package com.hitweb.controller;

import com.hitweb.entity.ClassCreate;
import com.hitweb.pojo.Classes;
import com.hitweb.pojo.Result;
import com.hitweb.service.ClassService;
import com.hitweb.pojo.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
@CrossOrigin
public class ClassController {
    private final ClassService classService;

    @DeleteMapping("/{ids}")
    public Result<String> delete(@PathVariable List<Integer> ids) {
        return classService.delete(ids);
    }

    @GetMapping("/all")
    public Result<List<Classes>> list() {
        return classService.list();
    }

    @PostMapping
    public Result<String> add(@RequestBody ClassCreate classCreate) {
        return classService.add(classCreate);
    }

    @PutMapping
    public Result<String> update(@RequestBody Classes classes) {
        return classService.update(classes);
    }

    @GetMapping("/{id}")
    public Result<Classes> getById(@PathVariable Integer id) {
        return classService.getById(id);
    }

    @GetMapping
    public Result<PageResult<Classes>> page(@RequestParam(required = false) String name,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                                             @RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return classService.getClassPage(name, begin, end, page, pageSize);
    }
}
