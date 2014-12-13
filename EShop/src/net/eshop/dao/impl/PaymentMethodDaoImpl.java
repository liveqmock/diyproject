/*
 * 
 * 
 * 
 */
package net.eshop.dao.impl;

import net.eshop.dao.PaymentMethodDao;
import net.eshop.entity.PaymentMethod;

import org.springframework.stereotype.Repository;

/**
 * Dao - 支付方式
 * 
 * 
 * 
 */
@Repository("paymentMethodDaoImpl")
public class PaymentMethodDaoImpl extends BaseDaoImpl<PaymentMethod, Long> implements PaymentMethodDao {

}