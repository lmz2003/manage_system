package com.hitweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureTeacherBind {
    private Integer courseId;
    private List<Integer> addIds;
    private List<Integer> removeIds;
}
