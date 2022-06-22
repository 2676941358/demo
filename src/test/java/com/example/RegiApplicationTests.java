package com.example;



import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
@MapperScan("com.example.Mapper")
class RegiApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {

        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("dd","dd");
        List<String> range = listOperations.range("dd",0,-1);
        for (String o : range) {
            System.out.println(o);
        }
        Long dd = listOperations.size("dd");
        int i = dd.intValue();
        System.out.println(i);
        for (int i1 = 0; i1 < i; i1++) {
           listOperations.rightPop("dd");
        }
    }
        @Test
    public void set(){

        }
    }


