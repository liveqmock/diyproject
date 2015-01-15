/*
 * 
 * 
 * 
 */
package net.eshop.service;

import java.util.List;

import net.eshop.entity.AdminMenu;

/**
 * Service - 菜单
 * 
 * 
 * 
 */
public interface AdminMenuService extends BaseService<AdminMenu, Long> {

	/**
	 * 查找顶级菜单
	 * 
	 * @return 顶级菜单
	 */
	List<AdminMenu> findRoots(String cacheRegion);	

}