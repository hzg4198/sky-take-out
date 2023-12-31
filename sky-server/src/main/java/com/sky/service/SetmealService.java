package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

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

	/**
	 * 批量删除套餐
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);

	/**
	 * 根据id查询套餐
	 * @param id
	 * @return
	 */
	SetmealVO getByIdWithDish(Long id);

	/**
	 * 修改套餐
	 * @param setmealDTO
	 */
	void updateWithDish(SetmealDTO setmealDTO);

	/**
	 * 套餐起售停售
	 * @param status
	 * @param id
	 */
	void startOrStop(Integer status, Long id);
}
