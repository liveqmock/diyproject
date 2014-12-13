/*
 * 
 * 
 * 
 */
package net.eshop.dao.impl;

import net.eshop.dao.OrderItemDao;
import net.eshop.entity.OrderItem;

import org.springframework.stereotype.Repository;

/**
 * Dao - 订单项
 * 
 * 
 * 
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

}