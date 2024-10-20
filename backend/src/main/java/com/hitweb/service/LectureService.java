package com.hitweb.service;

import com.hitweb.entity.LectureCreate;
import com.hitweb.entity.LectureTeacherBind;
import com.hitweb.entity.LectureUpdate;
import com.hitweb.pojo.Lecture;
import com.hitweb.pojo.Result;
import com.hitweb.pojo.PageResult;
import com.hitweb.pojo.Teacher;

import java.time.LocalDate;
import java.util.List;

public interface LectureService {
    Result<String> save(LectureCreate lectureCreate);

    Result<String> delete(Integer id);

    Result<String> update(LectureUpdate lectureUpdate);

    Result<List<Lecture>> list();

    Result<Lecture> getById(Integer id);

    Result<String> bindLectureTeacher(LectureTeacherBind lectureTeacherBind);

    Result<PageResult<Lecture>> page(String name, String room, LocalDate begin, LocalDate end, Integer page, Integer pageSize);

    Result<List<Teacher>> getTeacher(Integer id);
}
