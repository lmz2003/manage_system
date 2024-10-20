package com.hitweb.service;

import com.hitweb.entity.DeptCreate;
import com.hitweb.pojo.Dept;
import com.hitweb.pojo.Result;

import java.util.List;


public interface DeptService{
    Result<List<Dept>> list();
    Result<String> delete(List<Integer> ids);

    Result<String> add(DeptCreate name);

    Result<Dept> getById(Integer id);

    Result<String> update(Integer id, String name);
}
