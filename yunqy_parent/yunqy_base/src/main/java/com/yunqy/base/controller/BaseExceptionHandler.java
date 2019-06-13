package com.yunqy.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通用异常类
 * @ControllerAdvice : 控制层的通知标签
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public Result error (Exception e) {
        e.printStackTrace();
        return new Result(false,StatusCode.ERROR,e.getMessage());
    }
}
