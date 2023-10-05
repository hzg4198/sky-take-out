package com.sky.controller.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.google.gson.annotations.Expose;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
public class SetmealController {

	@Autowired
	private SetmealService setmealService;

	@PostMapping
	@ApiOperation("添加套餐")
	public Result addSetmeal(@RequestBody SetmealDTO setmealDTO){
		setmealService.saveWithDish(setmealDTO);
		return Result.success();
	}

	@GetMapping("/page")
	@ApiOperation("分页查询")
	public Result<PageResult> page(SetmealPageQueryDTO pageQueryDTO){
		PageResult pageResult = setmealService.pageQuery(pageQueryDTO);
		return  Result.success(pageResult);
	}

	@DeleteMapping
	@ApiOperation("删除菜品")
	public Result del(@RequestParam List<Long> ids){
		setmealService.deleteBatch(ids);
		return Result.success();
	}

	@GetMapping("/{id}")
	@ApiOperation("根据id查询套餐")
	public Result<SetmealVO> getById(@PathVariable Long id){
		SetmealVO setmealVO = setmealService.getByIdWithDish(id);
		return Result.success(setmealVO);
	}

	@PutMapping
	@ApiOperation("修改套餐")
	public Result update(@RequestBody SetmealDTO setmealDTO){
		setmealService.updateWithDish(setmealDTO);
		return Result.success();
	}

	@PostMapping("/status/{status}")
	public Result startOrStop(@PathVariable Integer status, Long id){
		setmealService.startOrStop(status, id);
		return Result.success();
	}


}
