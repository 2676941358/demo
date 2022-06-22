package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Mapper.EmployeeMapper;
import com.example.entity.Employee;
import com.example.service.eservice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class esImpl extends ServiceImpl<EmployeeMapper, Employee> implements eservice {
}
