package com.hitweb.controller;

import com.hitweb.entity.StudentCreate;
import com.hitweb.entity.StudentUpdate;
import com.hitweb.pojo.Result;
import com.hitweb.pojo.Student;
import com.hitweb.service.StudentService;
import com.hitweb.pojo.Gender;
import com.hitweb.pojo.PageResult;
import com.hitweb.pojo.StudentClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RequestMapping("/student")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public Result<String> add(@RequestBody StudentCreate studentCreate) {
        log.info("studentCreate:{}", studentCreate);
        return studentService.add(studentCreate);
    }

    @DeleteMapping("/{ids}")
    public Result<String> delete(@PathVariable List<Integer> ids) {
        log.info("delete ids:{}", ids);
        return studentService.delete(ids);
    }
    @GetMapping("/{id}")
    public Result<Student> getById(@PathVariable Integer id) {
        log.info("getById students id:{}", id);
        return studentService.getById(id);
    }

    @GetMapping("/all")
    public Result<List<Student>> list() {
        log.info("list students");
        return studentService.list();
    }

    @PutMapping
    public Result<String> update(@RequestBody StudentUpdate student) {
        log.info("update student:{}", student);
        return studentService.update(student);
    }

    @GetMapping("/dis")
    public Result<String> dis(@RequestParam Integer id,@RequestParam Integer score) {
        log.info("dis student:{}", id);
        return studentService.dis(id,score);
    }

    @GetMapping
    public Result<PageResult<Student>> page(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String registration,
                                            @RequestParam(required = false) Integer education,
                                            @RequestParam(required = false) Integer classId,
                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        log.info("page students name:{},registration:{},education:{},classId:{},page:{},pageSize:{}", name, registration,education, classId, page, pageSize);
        return studentService.page(name, registration,education, classId, page, pageSize);
    }

    @GetMapping("/gender")
    public Result<List<Gender>> getGender() {
        log.info("getGender students");
        return studentService.getGender();
    }

    @GetMapping("/class")
    public Result<List<StudentClass>> getClassVo() {
        log.info("getClass students");
        return studentService.getClassVo();
    }
}
