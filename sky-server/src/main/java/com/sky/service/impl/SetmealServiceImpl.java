package com.sky.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealDishService;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.ognl.EnumerationIterator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

	/**
	 * 批量删除套餐
	 * @param ids
	 */
	@Override
	public void deleteBatch(List<Long> ids) {
		ids.forEach(id -> {
			Setmeal setmeal = setMealMapper.selectById(id);
			if(StatusConstant.ENABLE == setmeal.getStatus()){
				//起售中的套餐不能移除
				throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
			}
		});
		ids.forEach(setmealId -> {
			//删除套餐表中的数据
			setMealMapper.deleteById(setmealId);
			//删除套餐菜品关系表中的数据
			QueryWrapper<SetmealDish> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(SetmealDish::getSetmealId,setmealId);
			setmealDishService.getBaseMapper().delete(queryWrapper);
		});
	}

	/**
	 * 根据id查询套餐
	 * @param id
	 * @return
	 */
	@Override
	public SetmealVO getByIdWithDish(Long id) {
		SetmealVO setmealVO = new SetmealVO();
		Setmeal setmeal = setMealMapper.selectById(id);
		QueryWrapper<SetmealDish> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SetmealDish::getSetmealId,id);
		List<SetmealDish> setmealDishes = setmealDishService.getBaseMapper().selectList(queryWrapper);

		BeanUtils.copyProperties(setmeal, setmealVO);
		setmealVO.setSetmealDishes(setmealDishes);

		return setmealVO;
	}

	/**
	 * 修改套餐
	 * @param setmealDTO
	 */
	@Override
	public void updateWithDish(SetmealDTO setmealDTO) {
		Setmeal setmeal = new Setmeal();
		BeanUtils.copyProperties(setmealDTO,setmeal);

		setMealMapper.updateById(setmeal);


	}

	/**
	 * 套餐起售停售
	 * @param status
	 * @param id
	 */
	@Override
	public void startOrStop(Integer status, Long id) {
		if (status == StatusConstant.ENABLE){
			List<Dish> dishList = dishMapper.getBySetMealId(id);
			if(dishList != null && dishList.size() > 0){
				dishList.forEach(o -> {
					if(o.getStatus().equals(StatusConstant.DISABLE)){
						throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
					}
				});
			}
		}

		Setmeal setmeal = Setmeal.builder().id(id).status(status).build();
		setMealMapper.updateById(setmeal);
	}


}
