package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.DishDto;
import com.example.entity.Category;
import com.example.entity.Dish;
import com.example.entity.DishFlavor;
import com.example.service.CategoryService;
import com.example.service.DishFlavorService;
import com.example.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 菜品管理 前端控制器
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return R.success("新增菜品成功");}
    @GetMapping("/page")
    public R<Page> se2(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        //构造分页构造器
        Page<Dish> page2 = new Page<>(page, pageSize);
        Page<DishDto> page1 = new Page<>();
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(name != null,Dish::getName,name);
        dishLambdaQueryWrapper.orderByAsc(Dish::getUpdateTime);
       dishService.page(page2,dishLambdaQueryWrapper);

        BeanUtils.copyProperties(page2,page1,"records");
        List<Dish> records = page2.getRecords();


        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto1 = new DishDto();
            BeanUtils.copyProperties(item,dishDto1);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String name1 = category.getName();
dishDto1.getCategoryName();

            }return dishDto1;
        }).collect(Collectors.toList());
          page1.setRecords(list);
        return R.success(page2);
    }
    @GetMapping("/{id}")
    public R<DishDto>get(@RequestBody Long id){
        DishDto dishDto = dishService.get(id);
        return R.success(dishDto);
    }@PutMapping
    public R<String>updat(@RequestBody DishDto dishDto){
        dishService.update(dishDto);
        return R.success("ok");
    }
    @GetMapping("/list")
    private R<List<Dish>> listR(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        List<Long> dishStream =list.stream().map(Dish::getId).collect(Collectors.toList());
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.in(DishFlavor::getDishId,dishStream);
        List<DishFlavor> list1 = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
        List<DishDto> dishDtos = new ArrayList<>();
        for (Dish dish1 : list) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish1,dishDto);
            Long dishDtoId = dishDto.getId();
            List<DishFlavor> flavorStream = list1.stream().filter(dishFlavor -> dishDtoId.equals(dishFlavor.getDishId())).collect(Collectors.toList());
            dishDto.setFlavors(flavorStream);
            list.add(dishDto);
        }
        return R.success(list);
    }
}
