package com.hitweb.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lecture_teacher")
public class LectureTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lecture_id")
    private Integer lectureId;

    @Column(name = "teacher_id")
    private Integer teacherId;
}
