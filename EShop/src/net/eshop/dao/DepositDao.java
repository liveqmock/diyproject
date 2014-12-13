/*
 * 
 * 
 * 
 */
package net.eshop.dao;

import net.eshop.Page;
import net.eshop.Pageable;
import net.eshop.entity.Deposit;
import net.eshop.entity.Member;

/**
 * Dao - 预存款
 * 
 * 
 * 
 */
public interface DepositDao extends BaseDao<Deposit, Long> {

	/**
	 * 查找预存款分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 预存款分页
	 */
	Page<Deposit> findPage(Member member, Pageable pageable);

}