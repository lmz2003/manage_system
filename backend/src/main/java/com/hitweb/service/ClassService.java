package com.hitweb.service;

import com.hitweb.entity.ClassCreate;
import com.hitweb.pojo.Classes;
import com.hitweb.pojo.Result;
import com.hitweb.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface ClassService {
    Result<String> delete(List<Integer> ids);

    Result<List<Classes>> list();

    Result<String> add(ClassCreate classCreate);

    Result<String> update(Classes classes);

    Result<Classes> getById(Integer id);

    Result<PageResult<Classes>> getClassPage(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize);
}
