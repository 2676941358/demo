package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Mapper.EmployeeMapper;
import com.example.Mapper.OrderDetailMapper;
import com.example.entity.Employee;
import com.example.entity.OrderDetail;
import com.example.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
