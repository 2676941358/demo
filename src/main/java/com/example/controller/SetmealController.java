package com.example.controller;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.dto.SetmealDto;
import com.example.entity.Category;
import com.example.entity.Setmeal;
import com.example.service.CategoryService;
import com.example.service.SetmealDishService;
import com.example.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */
@Mapper
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息：{}", setmealDto);
        setmealService.save(setmealDto);
        return R.success("ok");

    }

//    @GetMapping("/page")
//    public R<Page> page(int page, int pagesize, String name) {
//        Page<Setmeal> setmealPage = new Page<>(page, pagesize);
//        LambdaQueryWrapper<Setmeal> Wrapper = new LambdaQueryWrapper<>();
//        Wrapper.like(name != null, Setmeal::getName, name);
//        Wrapper.orderByDesc(Setmeal::getCreateTime);
//        setmealService.page(setmealPage, Wrapper);
//        List<Setmeal> records = setmealPage.getRecords();
//
//        List<SetmealDto> list = records.stream().map((item) -> {
//            SetmealDto setmealDto = new SetmealDto();
//            //对象拷贝
//            BeanUtils.copyProperties(item,setmealDto);
//            //分类id
//            Long categoryId = item.getCategoryId();
//            //根据分类id查询分类对象
//            Category category = categoryService.getById(categoryId);
//            if(category != null){
//                //分类名称
//                String categoryName = category.getName();
//                setmealDto.setCategoryName(categoryName);
//            }
//            return setmealDto;
//        }).collect(Collectors.toList());

 //       Set<Long> catgroIds = records.stream().map(setmeal -> setmeal.getCategoryId()).collect(Collectors.toSet());
  //      List<Category> categoryList = categoryService.listByIds(catgroIds);
 //       Map<Long, String> categoryMap = categoryList.stream().collect(Collectors.toMap(Category::getId, Category::getName));
        //      for (Setmeal setmeal : records) {
     //       Long categoryId = setmeal.getCategoryId();
    //        String categoryName = categoryMap.get(categoryId);
    //        setmeal.setCategoryName(categoryName);
   //     }

//        setmealPage.setRecords(list);
//        return R.success(setmealPage);
//    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name != null,Setmeal::getName,name);
        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }
    @DeleteMapping
    public R<String>delect(@RequestBody List<Long> id){

        setmealService.removeWithDish(id);

        return R.success("套餐数据删除成功");
    }

}