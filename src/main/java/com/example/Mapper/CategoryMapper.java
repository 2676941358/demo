package com.example.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.Locale;

/**
 * <p>
 * 菜品及套餐分类 Mapper 接口
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */ @Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
