package com.hitweb.service.impl;

import com.hitweb.entity.EmpCreate;
import com.hitweb.entity.EmpUpdate;
import com.hitweb.pojo.Emp;
import com.hitweb.repository.LectureTeacherRepository;
import com.hitweb.pojo.Gender;
import com.hitweb.pojo.EmpJob;
import com.hitweb.pojo.PageResult;
import com.hitweb.pojo.Result;
import com.hitweb.repository.EmpRepository;
import com.hitweb.service.EmpService;
import com.hitweb.utils.AliOssUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hitweb.message.Message.DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class EmpServiceImpl implements EmpService {
    private final EmpRepository empRepository;
    private final AliOssUtils aliOssUtils;
    private final LectureTeacherRepository lectureTeacherRepository;



    @Override
    public Result<PageResult<Emp>> getEmpPage(String name, Short gender, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
        Specification<Emp> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(name != null && !name.equals("")){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if(gender != null){
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }
            if(begin != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("entrydate"), begin));
            }
            if(end != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("entrydate"), end));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Emp> emps = empRepository.findAll(spec, PageRequest.of(page - 1, pageSize));
        return Result.success(new PageResult<Emp>((int) emps.getTotalElements(), emps.getContent()));

    }

    @Override
    @Transactional
    public Result<Void> delete(List<Integer> ids) {
        empRepository.deleteAllById(ids);
        lectureTeacherRepository.deleteAllByTeacherIdIn(ids);
        return Result.success();
    }

    @Override
    public Result<Void> save(EmpCreate emp) {
        Emp empSave = new Emp();
        BeanUtils.copyProperties(emp,empSave);
        empSave.setCreateTime(LocalDateTime.now());
        empSave.setUpdateTime(LocalDateTime.now());
        empSave.setPassword("123456");
        empRepository.save(empSave);
        return Result.success();
    }

    @Override
    public Result<Emp> getById(Integer id) {
        Optional<Emp> empOptional = empRepository.findById(id);
        return empOptional.map(Result::success).orElseGet(() -> Result.error(DATA_NOT_FOUND));
    }

    @Override
    public Result<Void> update(EmpUpdate empUpdate) {
        Optional<Emp> empOptional = empRepository.findById(empUpdate.getId());
        if(empOptional.isPresent()){
            Emp emp = empOptional.get();
            BeanUtils.copyProperties(empUpdate,emp);
            emp.setUpdateTime(LocalDateTime.now());
            empRepository.save(emp);
            return Result.success();
        }
        return Result.error(DATA_NOT_FOUND);
    }

    @Override
    public Result<String> upload(MultipartFile file) throws IOException {
        String url = aliOssUtils.upload(file);
        log.info("上传成功，url:{}",url);
        return Result.success(url);
    }

    @Override
    public Result<List<Emp>> list() {
        return Result.success(empRepository.findAll());
    }

    @Override
    public Result<List<Gender>> getGender() {
        Integer male = empRepository.findAllByGender((short) 1).size();
        Integer female = empRepository.findAllByGender((short) 2).size();
        return Result.success(List.of(new Gender("男性员工",male),new Gender("女姓员工",female)));
    }

    @Override
    public Result<List<EmpJob>> getJob() {
        int job1 = empRepository.findAllByJob((short) 1).size();
        int job2 = empRepository.findAllByJob((short) 2).size();
        int job3 = empRepository.findAllByJob((short) 3).size();
        int job4 = empRepository.findAllByJob((short) 4).size();
        int job5 = empRepository.findAllByJob((short) 5).size();
        return Result.success(List.of(new EmpJob("班主任",job1)
                ,new EmpJob("讲师",job2)
                ,new EmpJob("教研主管",job3)
                ,new EmpJob("学工主管",job4)
                ,new EmpJob("咨询师",job5)));
    }

    @Override
    public Result<List<Emp>> getTeacher() {
        return Result.success(empRepository.findAllByJob((short) 2));
    }

}
