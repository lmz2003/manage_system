package com.hitweb.controller;

import com.hitweb.entity.LectureCreate;
import com.hitweb.entity.LectureTeacherBind;
import com.hitweb.entity.LectureUpdate;
import com.hitweb.pojo.Lecture;
import com.hitweb.pojo.Result;
import com.hitweb.service.LectureService;
import com.hitweb.pojo.PageResult;
import com.hitweb.pojo.Teacher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@CrossOrigin
@RequestMapping("/lecture")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
public class LectureController {
    private final LectureService lectureService;

    @PostMapping
    public Result<String> save(@RequestBody LectureCreate lectureCreate) {
        log.info("lectureCreate:{}", lectureCreate);
        return lectureService.save(lectureCreate);
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        log.info("lecture id:{}", id);
        return lectureService.delete(id);
    }

    @PutMapping
    public Result<String> update(@RequestBody LectureUpdate lectureUpdate) {
        log.info("lectureUpdate:{}", lectureUpdate);
        return lectureService.update(lectureUpdate);
    }

    @GetMapping("/all")
    public Result<List<Lecture>> list() {
        log.info("lecture list");
        return lectureService.list();
    }

    @GetMapping("/{id}")
    public Result<Lecture> getById(@PathVariable Integer id) {
        log.info("lecture id:{}", id);
        return lectureService.getById(id);
    }

    @PutMapping("/bind")
    public Result<String> bindLectureTeacher(@RequestBody LectureTeacherBind lectureTeacherBind){
        log.info("lectureTeacherBind:{}", lectureTeacherBind);
        return lectureService.bindLectureTeacher(lectureTeacherBind);
    }

    @GetMapping
    public Result<PageResult<Lecture>> page(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String room,
                                            @RequestParam(required = false) LocalDate begin,
                                            @RequestParam(required = false) LocalDate end,
                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        log.info("name:{},room:{},begin:{},end:{},page:{},pageSize:{}", name, room, begin, end, page, pageSize);
        return lectureService.page(name, room, begin, end, page, pageSize);
    }

    @GetMapping("/teacher/{id}")
    public Result<List<Teacher>> getTeacher(@PathVariable Integer id){
        log.info("lecture id:{}", id);
        return lectureService.getTeacher(id);
    }
}
