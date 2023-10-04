package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;

public interface SetmealService extends IService<Setmeal> {

	/**
	 * 添加套餐
	 * @param setmealDTO
	 */
	void saveWithDish(SetmealDTO setmealDTO);

	/**
	 * 套餐分页查询
	 * @param pageQueryDTO
	 * @return
	 */
	PageResult pageQuery(SetmealPageQueryDTO pageQueryDTO);
}
