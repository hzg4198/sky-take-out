package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlavorMapper extends BaseMapper<DishFlavor> {

    @Override
    @AutoFill(OperationType.INSERT)
    int insert(DishFlavor entity);
}
