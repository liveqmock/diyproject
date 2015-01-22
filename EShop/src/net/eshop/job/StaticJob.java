/*
 * 
 * 
 * 
 */
package net.eshop.job;

import java.util.logging.Logger;

import javax.annotation.Resource;

import net.eshop.service.StaticService;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ibm.icu.util.Calendar;

/**
 * Job - 静态化
 * 
 * 
 * 
 */
@Component("staticJob")
@Lazy(false)
public class StaticJob {

	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	/** logger */
	private static final Logger logger = Logger.getLogger(StaticJob.class.getName());
	/**
	 * 生成静态
	 */
	@Scheduled(cron = "${job.static_build.cron}")
	public void build() {
		logger.info("Job started at "+Calendar.getInstance().getTime().toString());
		staticService.buildAll();
		logger.info("Job finished at "+Calendar.getInstance().getTime().toString());
	}

}