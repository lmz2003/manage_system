package com.hitweb.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.hitweb.message.Message.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreate {
    @NotNull(message = NAME_NOT_NULL)
    private String name;
    @NotNull(message = REGISTRATION_NOT_NULL)
    private String registration;
    @NotNull(message = GENDER_NOT_NULL)
    private Short gender;
    private String phone;
    private Integer education;
    private Integer classId;
}
