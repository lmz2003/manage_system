package com.hitweb.repository;

import com.hitweb.pojo.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

    Page<Lecture> findAll(Specification<Lecture> spec, Pageable of);
}
