/*
 *
 *
 *
 */
package net.eshop.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import net.eshop.CommonAttributes;
import net.eshop.EnumConverter;
import net.eshop.Setting;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;


/**
 * Utils - 系统设置
 *
 *
 *
 */
@SuppressWarnings("unchecked")
public final class SettingUtils
{

	/** CacheManager */
	private static final CacheManager cacheManager = CacheManager.create();

	/** BeanUtilsBean */
	private static final BeanUtilsBean beanUtils;

	static
	{
		final ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean()
		{
			@Override
			public String convert(final Object value)
			{
				if (value != null)
				{
					final Class<?> type = value.getClass();
					if (type.isEnum() && super.lookup(type) == null)
					{
						super.register(new EnumConverter(type), type);
					}
					else if (type.isArray() && type.getComponentType().isEnum())
					{
						if (super.lookup(type) == null)
						{
							final ArrayConverter arrayConverter = new ArrayConverter(type, new EnumConverter(type.getComponentType()), 0);
							arrayConverter.setOnlyFirstToString(false);
							super.register(arrayConverter, type);
						}
						final Converter converter = super.lookup(type);
						return ((String) converter.convert(String.class, value));
					}
				}
				return super.convert(value);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(final String value, final Class clazz)
			{
				if (clazz.isEnum() && super.lookup(clazz) == null)
				{
					super.register(new EnumConverter(clazz), clazz);
				}
				return super.convert(value, clazz);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(final String[] values, final Class clazz)
			{
				if (clazz.isArray() && clazz.getComponentType().isEnum() && super.lookup(clazz.getComponentType()) == null)
				{
					super.register(new EnumConverter(clazz.getComponentType()), clazz.getComponentType());
				}
				return super.convert(values, clazz);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(final Object value, final Class targetType)
			{
				if (super.lookup(targetType) == null)
				{
					if (targetType.isEnum())
					{
						super.register(new EnumConverter(targetType), targetType);
					}
					else if (targetType.isArray() && targetType.getComponentType().isEnum())
					{
						final ArrayConverter arrayConverter = new ArrayConverter(targetType, new EnumConverter(
								targetType.getComponentType()), 0);
						arrayConverter.setOnlyFirstToString(false);
						super.register(arrayConverter, targetType);
					}
				}
				return super.convert(value, targetType);
			}
		};

		final DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(CommonAttributes.DATE_PATTERNS);
		convertUtilsBean.register(dateConverter, Date.class);
		beanUtils = new BeanUtilsBean(convertUtilsBean);
	}

	/**
	 * 不可实例化
	 */
	private SettingUtils()
	{
	}

	/**
	 * 获取系统设置
	 *
	 * @return 系统设置
	 */
	public static Setting get()
	{
		final Ehcache cache = cacheManager.getEhcache(Setting.CACHE_NAME);
		final net.sf.ehcache.Element cacheElement = cache.get(Setting.CACHE_KEY);
		Setting setting;
		if (cacheElement != null)
		{
			setting = (Setting) cacheElement.getObjectValue();
		}
		else
		{
			setting = new Setting();
			try
			{
				final File eshopXmlFile = new ClassPathResource(CommonAttributes.eshop_XML_PATH).getFile();
				final Document document = new SAXReader().read(eshopXmlFile);
				final List<Element> elements = document.selectNodes("/eshop/setting");
				for (final Element element : elements)
				{
					final String name = element.attributeValue("name");
					final String value = element.attributeValue("value");
					try
					{
						beanUtils.setProperty(setting, name, value);
					}
					catch (final IllegalAccessException e)
					{
						e.printStackTrace();
					}
					catch (final InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
			cache.put(new net.sf.ehcache.Element(Setting.CACHE_KEY, setting));
		}
		return setting;
	}

	/**
	 * 设置系统设置
	 *
	 * @param setting
	 *           系统设置
	 */
	public static void set(final Setting setting)
	{
		try
		{
			final File eshopXmlFile = new ClassPathResource(CommonAttributes.eshop_XML_PATH).getFile();
			final Document document = new SAXReader().read(eshopXmlFile);
			final List<Element> elements = document.selectNodes("/eshop/setting");
			for (final Element element : elements)
			{
				try
				{
					final String name = element.attributeValue("name");
					final String value = beanUtils.getProperty(setting, name);
					final Attribute attribute = element.attribute("value");
					attribute.setValue(value);
				}
				catch (final IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (final InvocationTargetException e)
				{
					e.printStackTrace();
				}
				catch (final NoSuchMethodException e)
				{
					e.printStackTrace();
				}
			}

			FileOutputStream fileOutputStream = null;
			XMLWriter xmlWriter = null;
			try
			{
				final OutputFormat outputFormat = OutputFormat.createPrettyPrint();
				outputFormat.setEncoding("UTF-8");
				outputFormat.setIndent(true);
				outputFormat.setIndent("	");
				outputFormat.setNewlines(true);
				fileOutputStream = new FileOutputStream(eshopXmlFile);
				xmlWriter = new XMLWriter(fileOutputStream, outputFormat);
				xmlWriter.write(document);
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (xmlWriter != null)
				{
					try
					{
						xmlWriter.close();
					}
					catch (final IOException e)
					{
						e.printStackTrace();
					}
				}
				IOUtils.closeQuietly(fileOutputStream);
			}

			final Ehcache cache = cacheManager.getEhcache(Setting.CACHE_NAME);
			cache.put(new net.sf.ehcache.Element(Setting.CACHE_KEY, setting));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

}