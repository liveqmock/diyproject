/*
 * 
 * 
 * 
 */
package net.eshop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.eshop.dao.AdminMenuDao;
import net.eshop.entity.AdminMenu;
import net.eshop.entity.ArticleCategory;
import net.eshop.service.AdminMenuService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 系统菜单
 * 
 * 
 * 
 */
@Service("adminMenuServiceImpl")
public class AdminMenuServiceImpl extends BaseServiceImpl<AdminMenu, Long> implements AdminMenuService {

	@Resource(name = "adminMenuDaoImpl")
	private AdminMenuDao adminMenuDao;

	@Resource(name = "adminMenuDaoImpl")
	public void setBaseDao(AdminMenuDao adminMenuDao) {
		super.setBaseDao(adminMenuDao);
	}

	@Override
	@Cacheable("adminMenu")
	public List<AdminMenu> findRoots(String cacheRegion) {		
		return adminMenuDao.findRoots(80);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "adminMenu"}, allEntries = true)
	public void save(AdminMenu adminMenu) {
		super.save(adminMenu);
	}
}