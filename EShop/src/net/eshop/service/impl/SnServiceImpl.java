/*
 * 
 * 
 * 
 */
package net.eshop.service.impl;

import javax.annotation.Resource;

import net.eshop.dao.SnDao;
import net.eshop.entity.Sn.Type;
import net.eshop.service.SnService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 序列号
 * 
 * 
 * 
 */
@Service("snServiceImpl")
public class SnServiceImpl implements SnService {

	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	@Transactional
	public String generate(Type type) {
		return snDao.generate(type);
	}

}