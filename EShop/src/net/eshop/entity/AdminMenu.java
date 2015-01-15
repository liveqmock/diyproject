package net.eshop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * 后台管理一二级菜单
 */
@Entity
@Table(name="t_admin_menu")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_admin_menu_sequence")
public class AdminMenu extends OrderEntity {

	
	private static final long serialVersionUID = 3726536879330249678L;
	
	/** 名称 */
	private String name;
	
	
	/**
	 * 权限代码 角色的权限列表来源以此. 
	 * @see net.eshop.entity.RoleRole
	 */
	private String authorityCode;

	/** url 链接 */
	private String url;

	/** 上级菜单 */
	private AdminMenu parent;
	
	
	/** 下级菜单 */
	private Set<AdminMenu> children = new HashSet<AdminMenu>();

	/**
	 * @return the name
	 */
	@NotEmpty
	@Length(max = 100)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * @return the authorityCode
	 */
	public String getAuthorityCode() {
		return authorityCode;
	}

	/**
	 * @param authorityCode the authorityCode to set
	 */
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the parent
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public AdminMenu getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(AdminMenu parent) {
		this.parent = parent;
	}
	
	/**
	 * @return the children
	 */
	@OneToMany(mappedBy = "parent",fetch=FetchType.EAGER)
	public Set<AdminMenu> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<AdminMenu> children) {
		this.children = children;
	}
}
