/*
 * 
 * 
 * 
 */
package net.eshop.dao.impl;

import net.eshop.dao.AdDao;
import net.eshop.entity.Ad;

import org.springframework.stereotype.Repository;

/**
 * Dao - 广告
 * 
 * 
 * 
 */
@Repository("adDaoImpl")
public class AdDaoImpl extends BaseDaoImpl<Ad, Long> implements AdDao {

}