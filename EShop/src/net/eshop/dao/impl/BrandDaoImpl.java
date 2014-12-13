/*
 * 
 * 
 * 
 */
package net.eshop.dao.impl;

import net.eshop.dao.BrandDao;
import net.eshop.entity.Brand;

import org.springframework.stereotype.Repository;

/**
 * Dao - 品牌
 * 
 * 
 * 
 */
@Repository("brandDaoImpl")
public class BrandDaoImpl extends BaseDaoImpl<Brand, Long> implements BrandDao {

}