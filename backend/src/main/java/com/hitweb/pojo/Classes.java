package com.hitweb.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "class")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String room;
    @Column(name = "emp_id")
    private Integer empId;
    @Column(name = "begin_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginTime;
    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
}
