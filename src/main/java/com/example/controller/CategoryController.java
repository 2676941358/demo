package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.entity.Category;
import com.example.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author 黑马程序员
 * @since 2022-06-06
 */
@Mapper
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("ok");
    }

    @GetMapping("/page")
    public R<Page> se2(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        //构造分页构造器
        Page<Category> page2 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> cate = new LambdaQueryWrapper<>();
        cate.orderByAsc(Category::getSort);

        //分页查询
        categoryService.page(page2, cate);
        return R.success(page2);
    }

    @DeleteMapping
    public R<String> delete(Long id) {
        log.info("删除分类，id为：{}", id);
        categoryService.removeById(id);
        return R.success("分类信息删除成功");
    }
    @PutMapping
    public R<String>put(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("ok");
    }
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}

