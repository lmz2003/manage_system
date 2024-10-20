package com.hitweb.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.hitweb.message.Message.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpCreate {
    @NotNull(message = USERNAME_NOT_NULL)
    private String username;
    @NotNull(message = NAME_NOT_NULL)
    private String name;
    @NotNull(message = GENDER_NOT_NULL)
    private Short gender;
    private LocalDate entrydate;
    private Integer deptId;
    private Short job;
    private String image;
}
