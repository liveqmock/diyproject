/*
 * 
 * 
 * 
 */
package net.eshop.job;

import java.util.logging.Logger;

import javax.annotation.Resource;

import net.eshop.service.OrderService;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ibm.icu.util.Calendar;

/**
 * Job - 订单
 * 
 * 
 * 
 */
@Component("orderJob")
@Lazy(false)
public class OrderJob {

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	
	private static final Logger logger = Logger.getLogger(OrderJob.class.getName());

	/**
	 * 释放过期订单库存
	 */
	@Scheduled(cron = "${job.order_release_stock.cron}")
	public void releaseStock() {
		logger.info("Job started at "+Calendar.getInstance().getTime().toString());
		orderService.releaseStock();
		logger.info("Job stopped at "+Calendar.getInstance().getTime().toString());
	}

}