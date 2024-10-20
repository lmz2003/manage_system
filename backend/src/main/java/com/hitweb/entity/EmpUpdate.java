package com.hitweb.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpUpdate {
    @NotNull(message = "id不能为空")
    private Integer id;
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "性别不能为空")
    private Short gender;
    private String image;
    private Integer deptId;
    private Short job;
    private Date entrydate;
}
