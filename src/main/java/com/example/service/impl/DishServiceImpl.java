package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Mapper.DishMapper;
import com.example.dto.DishDto;
import com.example.entity.Dish;
import com.example.entity.DishFlavor;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * <p>
 * 菜品管理 服务实现类
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long categoryId = dishDto.getCategoryId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(categoryId);
        }
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto get(Long id) {
        Dish dish=this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> dishFlavorL = new LambdaQueryWrapper<>();
        dishFlavorL.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavorService.list(dishFlavorL);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    public void update(DishDto dishDto) {
this.update(dishDto);
        LambdaQueryWrapper<DishFlavor> dishFlavor = new LambdaQueryWrapper<>();
        dishFlavor.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(dishFlavor);
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());
        }dishFlavorService.saveBatch(flavors);
    }
}
