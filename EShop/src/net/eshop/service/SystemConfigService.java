package net.eshop.service;

import net.eshop.entity.SystemConfig;


public interface SystemConfigService extends BaseService<SystemConfig, Long>
{
	SystemConfig getEncryptionConfig();

	void updateEncryptionString(final String encryption);
}
