package com.sky.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealDishService;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetmealService {
	@Autowired
	private SetMealMapper setMealMapper;
	@Autowired
	private DishMapper dishMapper;
	@Autowired
	private SetMealDishMapper setMealDishMapper;
	@Autowired
	private SetmealDishService setmealDishService;

	/**
	 * 新增套餐，保存套餐和菜品之间的关联关系
	 * @param setmealDTO
	 */
	@Override
	public void saveWithDish(SetmealDTO setmealDTO) {
		Setmeal setmeal = new Setmeal();
		BeanUtils.copyProperties(setmealDTO,setmeal);
		//插入套餐表
		setMealMapper.insert(setmeal);
		//获取返回的套餐id
		Long id = setmeal.getId();
		//获取对应的菜品
		List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
		setmealDishes.forEach(o->o.setSetmealId(id));
		setmealDishService.saveBatch(setmealDishes);

	}

	/**
	 * 套餐分页查询
	 * @param pageQueryDTO
	 * @return
	 */
	@Override
	public PageResult pageQuery(SetmealPageQueryDTO pageQueryDTO) {
		int pageSize = pageQueryDTO.getPageSize();
		int pageNum = pageQueryDTO.getPage();
		PageHelper.startPage(pageNum,pageSize);
		Page<SetmealVO> page = setMealMapper.pageQuery(pageQueryDTO);

		return new PageResult(page.getTotal(),page.getResult());
	}


}
