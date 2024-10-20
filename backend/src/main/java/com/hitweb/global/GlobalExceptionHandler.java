package com.hitweb.global;

import com.hitweb.pojo.Result;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.hitweb.message.Message.BAD_REQUEST_MSG;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public Result<List<String>> handleBindingException(BindException e)
    {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return Result.error(BAD_REQUEST_MSG, errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return Result.error(BAD_REQUEST_MSG, errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<List<String>> handleConstraintViolationException(ConstraintViolationException e){
        return Result.error(BAD_REQUEST_MSG, e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
    }
}
