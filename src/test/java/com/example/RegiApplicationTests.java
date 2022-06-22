package com.example;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.Mapper.EmployeeMapper;
import com.example.entity.Employee;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("com.example.Mapper")
class RegiApplicationTests {
    @Autowired
 private EmployeeMapper ordersMapper;
    @Test
    void contextLoads() {
        QueryWrapper<Employee> o = new QueryWrapper<>();
        List<Employee> o1 = ordersMapper.selectList(o);
        System.out.println(o1);
    }

}
