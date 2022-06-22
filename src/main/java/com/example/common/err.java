package com.example.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class err {
    @ExceptionHandler()
    public R<String> ex(SQLIntegrityConstraintViolationException sql) {
        log.error(sql.getMessage());
        if (sql.getMessage().contains("Duplicate entry")) {
            String[] split = sql.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("我不到啊");
    }

}