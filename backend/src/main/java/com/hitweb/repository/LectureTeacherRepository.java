package com.hitweb.repository;

import com.hitweb.pojo.LectureTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface LectureTeacherRepository extends JpaRepository<LectureTeacher, Integer> {
    List<LectureTeacher> findAllByLectureId(Integer id);

    boolean existsByLectureIdAndTeacherId(Integer lectureId, Integer teacherId);

    @Modifying
    void deleteByLectureIdAndTeacherId(Integer lectureId, Integer removeId);

    @Modifying
    void deleteAllByTeacherIdIn(List<Integer> ids);
}
