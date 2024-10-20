package com.hitweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.hitweb.message.Message.SUCCESS;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(1, SUCCESS, data);
    }

    public static <T> Result<T> success() {
        return new Result<>(1, SUCCESS, null);
    }

    public static  <T> Result<T> error(String msg,T data){
        return new Result<>(0, msg, data);
    }

    public static  <T> Result<T> error(String msg){
        return new Result<>(0, msg, null);
    }
}
