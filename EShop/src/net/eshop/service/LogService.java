/*
 * 
 * 
 * 
 */
package net.eshop.service;

import net.eshop.entity.Log;

/**
 * Service - 日志
 * 
 * 
 * 
 */
public interface LogService extends BaseService<Log, Long> {

	/**
	 * 清空日志
	 */
	void clear();

}