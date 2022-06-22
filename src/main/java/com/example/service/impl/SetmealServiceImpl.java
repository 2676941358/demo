package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Mapper.SetmealMapper;
import com.example.dto.SetmealDto;
import com.example.entity.Setmeal;
import com.example.entity.SetmealDish;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 套餐 服务实现类
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService service;
    @Transactional
    public void save(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        service.saveBatch(setmealDishes);
    }
@Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
    LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
    queryWrapper.in(Setmeal::getId,ids);
    queryWrapper.eq(Setmeal::getStatus,1);
    int count = this.count(queryWrapper);
    if(count > 0){
        //如果不能删除，抛出一个业务异常
        return;
    }

    //2)如果可以删除，先删除套餐表中的数据---setmeal
    //SQL:  delete from setmeal where id in (1,2,3)
    this.removeByIds(ids);

    //3)删除关系表中的数据----setmeal_dish
    //SQL:  delete from setmeal_dish where setmeal_id in (1,2,3)
    LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
    service.remove(lambdaQueryWrapper);
    }
}
