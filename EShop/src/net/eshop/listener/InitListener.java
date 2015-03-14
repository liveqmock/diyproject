/*
 *
 *
 *
 */
package net.eshop.listener;

import java.io.File;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.eshop.AuthenticationRealm;
import net.eshop.encryption.DESedeEncryption;
import net.eshop.entity.SystemConfig;
import net.eshop.service.CacheService;
import net.eshop.service.SearchService;
import net.eshop.service.StaticService;
import net.eshop.service.SystemConfigService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;


/**
 * Listener - 初始化
 *
 *
 *
 */
@Component("initListener")
public class InitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent>
{

	/** 安装初始化配置文件 */
	private static final String INSTALL_INIT_CONFIG_FILE_PATH = "/install_init.conf";

	/** logger */
	private static final Logger logger = Logger.getLogger(InitListener.class.getName());

	/** servletContext */
	private ServletContext servletContext;

	@Value("${system.name}")
	private String systemName;
	@Value("${system.version}")
	private String systemVersion;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;

	@Resource(name = "systemConfigServiceImpl")
	private SystemConfigService systemConfigService;

	@Override
	public void setServletContext(final ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent)
	{
		if (servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null)
		{
			final String info = "I|n|i|t|i|a|l|i|z|i|n|g|  " + systemName + " | " + systemVersion;
			logger.info(info.replace("|", ""));
			final File installInitConfigFile = new File(servletContext.getRealPath(INSTALL_INIT_CONFIG_FILE_PATH));
			if (installInitConfigFile.exists())
			{
				cacheService.clear();
				staticService.buildAll();
				searchService.purge();
				searchService.index();
				installInitConfigFile.delete();
			}
			else
			{
				staticService.buildIndex();
				staticService.buildOther();
			}
			if (isSoftwareAuthorized())
			{
				AuthenticationRealm.isAuthorized = true;
			}
			else
			{
				AuthenticationRealm.isAuthorized = false;
			}
		}
	}

	private boolean isSoftwareAuthorized()
	{
		final SystemConfig encryptionConfig = systemConfigService.getEncryptionConfig();
		if (encryptionConfig == null)
		{
			return false;
		}
		else
		{
			final String encryptedHex = encryptionConfig.getValue();
			return DESedeEncryption.matchMACAddress(encryptedHex);
		}
	}

}