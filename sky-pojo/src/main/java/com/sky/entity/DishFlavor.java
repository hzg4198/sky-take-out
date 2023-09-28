package com.sky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜品口味
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("dish_flavor")
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    //菜品id
    @TableField("dish_id")
    private Long dishId;

    //口味名称
    private String name;

    //口味数据list
    private String value;

}
