package com.hitweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureCreate {
    private String name;
    private String description;
    private String room;
    private LocalDate beginTime;
    private LocalDate endTime;
}
