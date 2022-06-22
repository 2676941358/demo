package com.example.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import com.example.entity.Setmeal;
import com.example.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;


    private String categoryName;
}
