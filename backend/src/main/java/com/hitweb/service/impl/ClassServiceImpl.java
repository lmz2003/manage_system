package com.hitweb.service.impl;

import com.hitweb.entity.ClassCreate;
import com.hitweb.pojo.Classes;
import com.hitweb.pojo.Result;
import com.hitweb.repository.ClassRepository;
import com.hitweb.service.ClassService;
import com.hitweb.pojo.PageResult;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.hitweb.message.Message.CREATE_ERROR_MSG;
import static com.hitweb.message.Message.UPDATE_ERROR_MSG;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;

    @Override
    public Result<String> delete(List<Integer> ids) {
        classRepository.deleteAllById(ids);
        return Result.success();
    }

    @Override
    public Result<List<Classes>> list() {
        return Result.success(classRepository.findAll());
    }

    @Override
    public Result<String> add(ClassCreate classCreate) {
        Classes classes = new Classes();
        try {
            BeanUtils.copyProperties(classCreate, classes);
            classRepository.save(classes);
        }catch (Exception e){
            return Result.error(CREATE_ERROR_MSG);
        }

        return Result.success();
    }

    @Override
    public Result<String> update(Classes classes) {
        Classes c = classRepository.findById(classes.getId()).orElse(null);
        if (c != null) {
            BeanUtils.copyProperties(classes, c);
            classRepository.save(c);
            return Result.success();
        }
        return Result.error(UPDATE_ERROR_MSG);
    }

    @Override
    public Result<Classes> getById(Integer id) {
        return Result.success(classRepository.findById(id).orElse(null));
    }

    @Override
    public Result<PageResult<Classes>> getClassPage(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
        Specification<Classes> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.equals("")) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (begin != null && end != null) {
                predicates.add(criteriaBuilder.between(root.get("endTime"), begin, end));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Classes> classes = classRepository.findAll(spec, PageRequest.of(page - 1, pageSize));
        return Result.success(new PageResult<>((int) classes.getTotalElements(), classes.getContent()));
    }
}
