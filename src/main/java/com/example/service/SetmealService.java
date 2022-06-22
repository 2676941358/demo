package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.SetmealDto;
import com.example.entity.Setmeal;
import org.apache.ibatis.annotations.Lang;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */
public interface SetmealService extends IService<Setmeal> {
public void save(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);
}
