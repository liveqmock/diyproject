/*
 * 
 * 
 * 
 */
package net.eshop.service;

import java.util.List;

import net.eshop.Filter;
import net.eshop.Order;
import net.eshop.entity.Navigation;
import net.eshop.entity.Navigation.Position;

/**
 * Service - 导航
 * 
 * 
 * 
 */
public interface NavigationService extends BaseService<Navigation, Long> {

	/**
	 * 查找导航
	 * 
	 * @param position
	 *            位置
	 * @return 导航
	 */
	List<Navigation> findList(Position position);

	/**
	 * 查找导航(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param cacheRegion
	 *            缓存区域
	 * @return 导航(缓存)
	 */
	List<Navigation> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion);

}