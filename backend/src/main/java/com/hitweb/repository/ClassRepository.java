package com.hitweb.repository;

import com.hitweb.pojo.Classes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<Classes, Integer> {
    Page<Classes> findAll(Specification<Classes> spec, Pageable pageable);

    Integer findIdByName(String s);

    Optional<Classes> findByName(String name);
}
