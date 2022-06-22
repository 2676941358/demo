package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.DishDto;
import com.example.entity.Dish;

/**
 * <p>
 * 菜品管理 服务类
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */
public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);
    public DishDto get(Long id);
    public void update(DishDto dishDto);
}
