package com.hitweb.repository;

import com.hitweb.pojo.Emp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer> {
    Page<Emp> findAll(Specification<Emp> spec, Pageable pageable);
    Emp findByUsernameAndPassword(String username, String password);
    List<Emp> findAllByGender(Short gender);
    List<Emp> findAllByJob(Short job);

    void deleteAllByDeptIdIn(List<Integer> ids);
}
