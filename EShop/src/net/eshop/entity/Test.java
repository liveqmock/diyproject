/*
 *
 *
 *
 */
package net.eshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Entity - 咨询
 *
 *
 *
 */
@Entity
@Table(name = "t_test")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_test_sequence")
public class Test extends BaseEntity
{


	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/consultation/content";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".jhtml";

	/** 内容 */
	private String content;

	/** 是否显示 */
	private Boolean isShow;



	/**
	 * 获取内容
	 *
	 * @return 内容
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	public String getContent()
	{
		return content;
	}

	/**
	 * 设置内容
	 *
	 * @param content
	 *           内容
	 */
	public void setContent(final String content)
	{
		this.content = content;
	}

	/**
	 * 获取是否显示
	 *
	 * @return 是否显示
	 */
	@Column(nullable = false)
	public Boolean getIsShow()
	{
		return isShow;
	}

	/**
	 * 设置是否显示
	 *
	 * @param isShow
	 *           是否显示
	 */
	public void setIsShow(final Boolean isShow)
	{
		this.isShow = isShow;
	}



}