package com.example.controller;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.entity.Category;
import com.example.entity.Employee;
import com.example.service.eservice;
import javafx.util.converter.LocalDateTimeStringConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Mapper
@Slf4j
@RestController
@RequestMapping("/employee")
public class controller {
@Autowired
    private eservice eservice;
@PostMapping("/login")
    public R<Employee> login(HttpServletRequest servletRequest , @RequestBody Employee employee){
    String password = employee.getPassword();
    password = DigestUtils.md5DigestAsHex(password.getBytes());
    LambdaQueryWrapper<Employee> em = new LambdaQueryWrapper<Employee>();
    em.eq(Employee::getUsername,employee.getUsername());
    Employee o = eservice.getOne(em);
    if (o==null){
        return R.error("登录失败了");
    }
    if (!o.getPassword().equals(password)){
        return R.error("密码错误");
    }
if (o.getStatus()==0){
    return R.error("账号封禁中");
}
servletRequest.getSession().setAttribute("employee",o.getId());
return R.success(o);
}
@PostMapping("/logout")
    public R<String>logout(HttpServletRequest request){
    request.getSession().removeAttribute("employee");
    return R.success("bye");
}
@PostMapping
public R<String>save(HttpServletRequest request, @RequestBody Employee employee){
    log.info("新增员工，员工信息：{}",employee.toString());
    employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
    employee.setCreateTime(LocalDateTime.now());
    employee.setUpdateTime(LocalDateTime.now());
    long employee1 = (long) request.getSession().getAttribute("employee");
    employee.setCreateUser(employee1);
    employee.setUpdateUser(employee1);
    eservice.save(employee);
    return R.success("成功");
    }
    @GetMapping("/page")
    public R<Page>se(int page,int pageSize,String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);
        //构造分页构造器
        Page<Employee> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> e = new LambdaQueryWrapper<>();
       // e.like(StringUtils.isNotColumnName(name), Employee::getName,name);
        e.orderByDesc(Employee::getUpdateTime);
        eservice.page(page1,e);
        return R.success(page1);
    }
    }


