package com.hitweb.service.impl;

import com.hitweb.entity.StudentCreate;
import com.hitweb.entity.StudentUpdate;
import com.hitweb.pojo.Classes;
import com.hitweb.pojo.Result;
import com.hitweb.pojo.Student;
import com.hitweb.repository.ClassRepository;
import com.hitweb.repository.StudentRepository;
import com.hitweb.service.StudentService;
import com.hitweb.pojo.Gender;
import com.hitweb.pojo.PageResult;
import com.hitweb.pojo.StudentClass;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hitweb.message.Message.DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    @Override
    public Result<String> add(StudentCreate studentCreate) {
        Student student = new Student();
        BeanUtils.copyProperties(studentCreate, student);
        student.setUpdateTime(LocalDateTime.now());
        student.setDisciplinary(0);
        student.setDisciplinaryScore(0);
        studentRepository.save(student);
        return Result.success();
    }

    @Override
    public Result<String> delete(List<Integer> ids) {
        studentRepository.deleteAllById(ids);
        return Result.success();
    }

    @Override
    public Result<Student> getById(Integer id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(Result::success).orElseGet(() -> Result.error(DATA_NOT_FOUND));
    }

    @Override
    public Result<List<Student>> list() {
        return Result.success(studentRepository.findAll());
    }

    @Override
    public Result<String> update(StudentUpdate studentUpdate) {
        Optional<Student> student = studentRepository.findById(studentUpdate.getId());
        if (student.isPresent()) {
            Student student1 = student.get();
            BeanUtils.copyProperties(studentUpdate, student1);
            student1.setUpdateTime(LocalDateTime.now());
            studentRepository.save(student1);
            return Result.success();
        }
        return Result.error(DATA_NOT_FOUND);
    }

    @Override
    public Result<String> dis(Integer id,Integer score) {
        Optional<Student> studentRepositoryById = studentRepository.findById(id);
        if (studentRepositoryById.isPresent()) {
            Student student = studentRepositoryById.get();
            student.setDisciplinary(student.getDisciplinary()+1);
            student.setDisciplinaryScore(student.getDisciplinaryScore()+score);
            student.setUpdateTime(LocalDateTime.now());
            studentRepository.save(student);
            return Result.success();
        }
        return Result.error(DATA_NOT_FOUND);
    }

    @Override
    public Result<PageResult<Student>> page(String name, String registration, Integer education, Integer classId, Integer page, Integer pageSize) {
        Specification<Student> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(name != null && !name.equals("")){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if(registration != null){
                predicates.add(criteriaBuilder.like(root.get("registration"), "%" + registration + "%"));
            }
            if(education != null){
                predicates.add(criteriaBuilder.equal(root.get("education"), education));
            }
            if(classId != null){
                predicates.add(criteriaBuilder.equal(root.get("classId"), classId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Student> students = studentRepository.findAll(spec, PageRequest.of(page - 1, pageSize));
        return Result.success(new PageResult<Student>((int) students.getTotalElements(), students.getContent()));
    }

    @Override
    public Result<List<Gender>> getGender() {
        int male = studentRepository.findAllByGender((short) 1).size();
        int female = studentRepository.findAllByGender((short) 2).size();
        return Result.success(List.of(new Gender("男性学员",male), new Gender("女性学员",female)));
    }

    @Override
    public Result<List<StudentClass>> getClassVo() {
        List<Classes> classes = studentRepository
                .findAll()
                .stream()
                .map(Student::getClassId)
                .distinct()
                .map(classRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        List<StudentClass> studentClasses = classes.stream()
                .map(cl -> new StudentClass(cl.getName(), studentRepository.countByClassId(cl.getId())))
                .collect(Collectors.toList());
        return Result.success(studentClasses);
    }
}
