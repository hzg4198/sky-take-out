package com.sky.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis自动填充字段
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		LocalDateTime now = LocalDateTime.now();
		Long id = BaseContext.getCurrentId();
		this.fillStrategy(metaObject,AutoFillConstant.CREATE_TIME,now);
		this.fillStrategy(metaObject,AutoFillConstant.UPDATE_TIME,now);
		this.fillStrategy(metaObject,AutoFillConstant.CREATE_USER,id);
		this.fillStrategy(metaObject,AutoFillConstant.UPDATE_USER,id);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		LocalDateTime now = LocalDateTime.now();
		Long id = BaseContext.getCurrentId();
		this.fillStrategy(metaObject,AutoFillConstant.UPDATE_TIME,now);
		this.fillStrategy(metaObject,AutoFillConstant.UPDATE_USER,id);

	}
}
