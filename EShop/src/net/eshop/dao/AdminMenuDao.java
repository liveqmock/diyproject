package net.eshop.dao;

import java.util.List;

import net.eshop.entity.AdminMenu;

public interface AdminMenuDao extends BaseDao<AdminMenu, Long>{
	/**
	 * 查找顶级菜单分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级菜单分类
	 */
	List<AdminMenu> findRoots(Integer count);

}
