/*
 * 
 * 
 * 
 */
package net.eshop.dao.impl;

import net.eshop.dao.CartItemDao;
import net.eshop.entity.CartItem;

import org.springframework.stereotype.Repository;

/**
 * Dao - 购物车项
 * 
 * 
 * 
 */
@Repository("cartItemDaoImpl")
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, Long> implements CartItemDao {

}