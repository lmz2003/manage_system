package com.hitweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassCreate {
    private String name;
    private String room;
    private LocalDate beginTime;
    private LocalDate endTime;
    private Integer empId;
}
