/*
 * 
 * 
 * 
 */
package net.eshop.dao.impl;

import javax.persistence.FlushModeType;

import net.eshop.dao.LogDao;
import net.eshop.entity.Log;

import org.springframework.stereotype.Repository;

/**
 * Dao - 日志
 * 
 * 
 * 
 */
@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

	public void removeAll() {
		String jpql = "delete from Log log";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
	}

}