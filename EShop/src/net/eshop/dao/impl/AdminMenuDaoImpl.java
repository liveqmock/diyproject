package net.eshop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import net.eshop.dao.AdminMenuDao;
import net.eshop.entity.AdminMenu;

import org.springframework.stereotype.Repository;

@Repository("adminMenuDaoImpl")
public class AdminMenuDaoImpl extends BaseDaoImpl<AdminMenu, Long> implements AdminMenuDao {

	public List<AdminMenu> findRoots(Integer count) {
		String jpql = "select adminMenu from AdminMenu adminMenu where adminMenu.parent is null order by adminMenu.order asc";
		TypedQuery<AdminMenu> query = entityManager.createQuery(jpql, AdminMenu.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

}