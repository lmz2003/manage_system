package com.hitweb.repository;

import com.hitweb.pojo.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Page<Student> findAll(Specification<Student> spec, Pageable pageable);
    List<Student> findAllByGender(Short gender);

    List<Student> findAllByClassId(Integer integer);

    Integer countByClassId(Integer id);
}
