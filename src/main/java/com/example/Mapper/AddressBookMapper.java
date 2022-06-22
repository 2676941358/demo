package com.example.Mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.AddressBook;

import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 地址管理 Mapper 接口
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}
