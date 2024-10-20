package com.hitweb.service.impl;

import com.hitweb.entity.LectureCreate;
import com.hitweb.entity.LectureTeacherBind;
import com.hitweb.entity.LectureUpdate;
import com.hitweb.pojo.*;
import com.hitweb.repository.EmpRepository;
import com.hitweb.repository.LectureRepository;
import com.hitweb.repository.LectureTeacherRepository;
import com.hitweb.service.LectureService;
import com.hitweb.pojo.PageResult;
import com.hitweb.pojo.Teacher;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hitweb.message.Message.*;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class LectureServiceImpl implements LectureService {
    private final LectureRepository lectureRepository;
    private final LectureTeacherRepository lectureTeacherRepository;
    private final EmpRepository empRepository;
    @Override
    public Result<String> save(LectureCreate lectureCreate) {
        Lecture lecture = new Lecture();
        try {
            BeanUtils.copyProperties(lectureCreate, lecture);
            lectureRepository.save(lecture);
        }catch (Exception e){
            return Result.error(CREATE_ERROR_MSG);
        }
        return Result.success();
    }

    @Override
    public Result<String> delete(Integer id) {
        lectureRepository.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<String> update(LectureUpdate lectureUpdate) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureUpdate.getId());
        if (lectureOptional.isPresent()){
            Lecture lecture = lectureOptional.get();
            BeanUtils.copyProperties(lectureUpdate, lecture);
            lectureRepository.save(lecture);
            return Result.success();
        }
        return Result.error(UPDATE_ERROR_MSG);
    }

    @Override
    public Result<List<Lecture>> list() {
        return Result.success(lectureRepository.findAll());
    }

    @Override
    public Result<Lecture> getById(Integer id) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(id);
        return lectureOptional.map(Result::success).orElseGet(() -> Result.error(DATA_NOT_FOUND));
    }

    @Override
    @Transactional
    public Result<String> bindLectureTeacher(LectureTeacherBind lectureTeacherBind) {
        Integer lectureId = lectureTeacherBind.getCourseId();
        List<Integer> addIds = lectureTeacherBind.getAddIds();
        List<Integer> removeIds = lectureTeacherBind.getRemoveIds();
        for (Integer addId : addIds){
            if (lectureTeacherRepository.existsByLectureIdAndTeacherId(lectureId, addId)){
                continue;
            }
            LectureTeacher lectureTeacher = new LectureTeacher();
            lectureTeacher.setLectureId(lectureId);
            lectureTeacher.setTeacherId(addId);
            lectureTeacherRepository.save(lectureTeacher);
        }
        for (Integer removeId : removeIds){
            lectureTeacherRepository.deleteByLectureIdAndTeacherId(lectureId, removeId);
        }
        return Result.success();
    }

    @Override
    public Result<PageResult<Lecture>> page(String name, String room, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
        Specification<Lecture> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(name != null && !name.equals("")){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if(room != null && !room.equals("")){
                predicates.add(criteriaBuilder.like(root.get("room"), "%" + room + "%"));
            }
            if(begin != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endTime"), begin));
            }
            if(end != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endTime"), end));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Lecture> classes = lectureRepository.findAll(spec, PageRequest.of(page - 1, pageSize));
        return Result.success(new PageResult<Lecture>((int) classes.getTotalElements(), classes.getContent()));
    }

    @Override
    public Result<List<Teacher>> getTeacher(Integer id) {
        List<String> names = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        lectureTeacherRepository.findAllByLectureId(id).forEach(lectureTeacher -> {
            int i = lectureTeacher.getTeacherId();
            Optional<Emp> empOptional = empRepository.findById(i);
            if (empOptional.isPresent()){
                Emp emp = empOptional.get();
                String name = emp.getName();
                Integer idEmp = emp.getId();
                names.add(name);
                ids.add(idEmp);
            }
            return;
        });
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            Teacher teacher = new Teacher();
            teacher.setName(names.get(i));
            teacher.setId(ids.get(i));
            teachers.add(teacher);
        }
        return Result.success(teachers);
    }
}
