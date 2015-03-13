package net.eshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 注意 如果column是数据库的关键字比如：key， 是不行的。 hibernate也无法自动更新数据库机构
 *
 * @author joeli2
 *
 */

@Entity
@Table(name = "t_system_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_systemconfig_sequence")
public class SystemConfig extends BaseEntity
{

	/**
	 *
	 */
	private static final long serialVersionUID = -7497975182031089600L;

	/**
	 * _type means properties name. this will be used in
	 */
	public static String _type = "type";

	public static String _code = "code";

	/**
	 * 配置类型
	 */
	private String type;

	/**
	 * 配置key
	 */
	private String code;


	/**
	 * 配置值
	 */
	private String value;

	/**
	 * @return the type
	 */
	@Column(nullable = false)
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 *           the type to set
	 */
	public void setType(final String type)
	{
		this.type = type;
	}

	/**
	 * @return the key
	 */
	@Column(nullable = false)
	public String getCode()
	{
		return code;
	}

	/**
	 * @param key
	 *           the key to set
	 */
	public void setCode(final String code)
	{
		this.code = code;
	}

	/**
	 * @return the value
	 */
	@Column(nullable = false)
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value
	 *           the value to set
	 */
	public void setValue(final String value)
	{
		this.value = value;
	}


}
