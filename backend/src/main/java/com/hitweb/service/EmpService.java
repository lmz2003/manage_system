package com.hitweb.service;

import com.hitweb.entity.EmpCreate;
import com.hitweb.pojo.Gender;
import com.hitweb.pojo.EmpJob;
import com.hitweb.pojo.PageResult;
import com.hitweb.entity.EmpUpdate;
import com.hitweb.pojo.Emp;
import com.hitweb.pojo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    Result<PageResult<Emp>> getEmpPage(String name, Short gender, LocalDate begin, LocalDate end, Integer page, Integer pageSize);

    Result<Void> delete(List<Integer> id);

    Result<Void> save(EmpCreate emp);

    Result<Emp> getById(Integer id);

    Result<Void> update(EmpUpdate empUpdate);

    Result<String> upload(MultipartFile file) throws IOException;

    Result<List<Emp>> list();

    Result<List<Gender>> getGender();

    Result<List<EmpJob>> getJob();

    Result<List<Emp>> getTeacher();

}
