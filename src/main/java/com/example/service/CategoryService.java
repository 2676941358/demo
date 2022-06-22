package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Category;

/**
 * <p>
 * 菜品及套餐分类 服务类
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */
public interface CategoryService extends IService<Category> {
 void remove(Long id);
}
