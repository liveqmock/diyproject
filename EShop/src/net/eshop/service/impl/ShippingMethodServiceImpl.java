/*
 * 
 * 
 * 
 */
package net.eshop.service.impl;

import javax.annotation.Resource;

import net.eshop.dao.ShippingMethodDao;
import net.eshop.entity.ShippingMethod;
import net.eshop.service.ShippingMethodService;

import org.springframework.stereotype.Service;

/**
 * Service - 配送方式
 * 
 * 
 * 
 */
@Service("shippingMethodServiceImpl")
public class ShippingMethodServiceImpl extends BaseServiceImpl<ShippingMethod, Long> implements ShippingMethodService {

	@Resource(name = "shippingMethodDaoImpl")
	public void setBaseDao(ShippingMethodDao shippingMethodDao) {
		super.setBaseDao(shippingMethodDao);
	}

}