package com.hitweb.service;

import com.hitweb.entity.StudentCreate;
import com.hitweb.entity.StudentUpdate;
import com.hitweb.pojo.Result;
import com.hitweb.pojo.Student;
import com.hitweb.pojo.Gender;
import com.hitweb.pojo.PageResult;
import com.hitweb.pojo.StudentClass;

import java.util.List;

public interface StudentService {
    Result<String> add(StudentCreate studentCreate);

    Result<String> delete(List<Integer> ids);

    Result<Student> getById(Integer id);

    Result<List<Student>> list();

    Result<String> update(StudentUpdate student);

    Result<String> dis(Integer id,Integer score);

    Result<PageResult<Student>> page(String name, String registration, Integer education, Integer classId, Integer page, Integer pageSize);

    Result<List<Gender>> getGender();

    Result<List<StudentClass>> getClassVo();
}
