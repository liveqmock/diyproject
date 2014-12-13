/*
 * 
 * 
 * 
 */
package net.eshop.service.impl;

import javax.annotation.Resource;

import net.eshop.dao.ParameterDao;
import net.eshop.entity.Parameter;
import net.eshop.service.ParameterService;

import org.springframework.stereotype.Service;

/**
 * Service - 参数
 * 
 * 
 * 
 */
@Service("parameterServiceImpl")
public class ParameterServiceImpl extends BaseServiceImpl<Parameter, Long> implements ParameterService {

	@Resource(name = "parameterDaoImpl")
	public void setBaseDao(ParameterDao parameterDao) {
		super.setBaseDao(parameterDao);
	}

}