package com.hitweb.service.impl;

import com.hitweb.entity.DeptCreate;
import com.hitweb.pojo.Dept;
import com.hitweb.pojo.Result;
import com.hitweb.repository.DeptRepository;
import com.hitweb.repository.EmpRepository;
import com.hitweb.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.hitweb.message.Message.DEPT_NOT_EXIST;
import static com.hitweb.message.Message.DEPT_NOT_NULL;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeptServiceImpl implements DeptService {
    private final DeptRepository deptRepository;
    private final EmpRepository empRepository;

    public Result<List<Dept>> list() {
        List<Dept> depts = deptRepository.findAll();
        return Result.success(depts);
    }

    public Result<String> delete(List<Integer> ids) {
        deptRepository.deleteAllById(ids);
        empRepository.deleteAllByDeptIdIn(ids);
        return Result.success();
    }

    @Override
    public Result<String> add(DeptCreate name) {
        Dept dept = new Dept();
        if (name == null || name.getName().equals("")){
            return Result.error(DEPT_NOT_NULL);
        }
        dept.setName(name.getName());
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptRepository.save(dept);
        return Result.success();
    }

    @Override
    public Result<Dept> getById(Integer id) {
        Optional<Dept> dept = deptRepository.findById(id);
        return dept.map(Result::success).orElseGet(() -> Result.error(DEPT_NOT_EXIST));
    }

    @Override
    public Result<String> update(Integer id, String name) {
        Optional<Dept> dept = deptRepository.findById(id);
        if (dept.isPresent()){
            Dept dept1 = dept.get();
            dept1.setName(name);
            dept1.setUpdateTime(LocalDateTime.now());
            deptRepository.save(dept1);
            return Result.success();
        }
        return Result.error(DEPT_NOT_EXIST);
    }
}

