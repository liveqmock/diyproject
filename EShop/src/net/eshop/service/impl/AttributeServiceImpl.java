/*
 * 
 * 
 * 
 */
package net.eshop.service.impl;

import javax.annotation.Resource;

import net.eshop.dao.AttributeDao;
import net.eshop.entity.Attribute;
import net.eshop.service.AttributeService;

import org.springframework.stereotype.Service;

/**
 * Service - 属性
 * 
 * 
 * 
 */
@Service("attributeServiceImpl")
public class AttributeServiceImpl extends BaseServiceImpl<Attribute, Long> implements AttributeService {

	@Resource(name = "attributeDaoImpl")
	public void setBaseDao(AttributeDao attributeDao) {
		super.setBaseDao(attributeDao);
	}

}