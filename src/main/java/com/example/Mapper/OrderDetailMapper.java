package com.example.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单明细表 Mapper 接口
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}
