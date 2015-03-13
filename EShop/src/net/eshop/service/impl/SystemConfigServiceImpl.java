package net.eshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.eshop.Filter;
import net.eshop.dao.BaseDao;
import net.eshop.dao.SystemConfigDao;
import net.eshop.entity.SystemConfig;
import net.eshop.service.SystemConfigService;

import org.springframework.stereotype.Service;


@Service("systemConfigServiceImpl")
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig, Long> implements SystemConfigService
{
	private static String ENCRYPTION_KEY = "ENCRYPTION";
	private static String TYPE_GLOBAL = "GLOBAL";

	@Resource(name = "systemConfigDaoImpl")
	private SystemConfigDao systemConfigDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see net.eshop.service.impl.BaseServiceImpl#setBaseDao(net.eshop.dao.BaseDao)
	 */
	@Override
	@Resource(name = "systemConfigDaoImpl")
	public void setBaseDao(final BaseDao<SystemConfig, Long> baseDao)
	{
		super.setBaseDao(systemConfigDao);
	}




	@Override
	public SystemConfig getEncryptionConfig()
	{
		final List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq(SystemConfig._type, TYPE_GLOBAL));
		filters.add(Filter.eq(SystemConfig._code, ENCRYPTION_KEY));
		final List<SystemConfig> list = findList(Integer.valueOf(1), filters, null);
		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更新密匙
	 *
	 * @param encryption
	 */
	public void updateEncryptionString(final String encryption)
	{
		SystemConfig encryptionConfig = getEncryptionConfig();
		if (encryptionConfig != null)
		{
			encryptionConfig.setValue(encryption);
			update(encryptionConfig);
		}
		else
		{
			encryptionConfig = new SystemConfig();
			encryptionConfig.setCode(ENCRYPTION_KEY);
			encryptionConfig.setType(TYPE_GLOBAL);
			encryptionConfig.setValue(encryption);
			save(encryptionConfig);
		}
	}

}
