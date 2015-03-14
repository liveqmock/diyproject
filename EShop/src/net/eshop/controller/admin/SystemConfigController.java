/*
 *
 *
 *
 */
package net.eshop.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.eshop.AuthenticationRealm;
import net.eshop.Message;
import net.eshop.Pageable;
import net.eshop.encryption.DESedeEncryption;
import net.eshop.entity.BaseEntity.Save;
import net.eshop.entity.SystemConfig;
import net.eshop.service.SystemConfigService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller - 会员
 *
 *
 *
 */
@Controller("systemConfigController")
@RequestMapping("/admin/systemConfig")
public class SystemConfigController extends BaseController
{

	@Resource(name = "systemConfigServiceImpl")
	private SystemConfigService systemConfigService;


	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(final Long id, final ModelMap model)
	{
		model.addAttribute("systemConfig", systemConfigService.find(id));
		return "/admin/systemConfig/view";
	}

	/**
	 * 添加
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(final ModelMap model)
	{
		return "/admin/systemConfig/add";
	}

	/**
	 * 保存
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(final SystemConfig systemConfig, final HttpServletRequest request,
			final RedirectAttributes redirectAttributes)
	{
		if (!isValid(systemConfig, Save.class))
		{
			return ERROR_VIEW;
		}

		systemConfigService.save(systemConfig);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(final Long id, final ModelMap model)
	{
		model.addAttribute("systemConfig", systemConfigService.find(id));
		return "/admin/systemConfig/edit";
	}

	/**
	 * 更新
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(final SystemConfig systemConfig, final HttpServletRequest request,
			final RedirectAttributes redirectAttributes)
	{
		if (!isValid(systemConfig))
		{
			return ERROR_VIEW;
		}
		systemConfigService.update(systemConfig);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(final Pageable pageable, final ModelMap model)
	{
		model.addAttribute("page", systemConfigService.findPage(pageable));
		return "/admin/systemConfig/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(final Long[] ids)
	{
		if (ids != null)
		{
			systemConfigService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String getMACHec(final ModelMap model)
	{
		model.addAttribute("authorizationID", DESedeEncryption.hecMAC());
		return "/admin/systemConfig/authorize";
	}

	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String authorizeSoftWare(final String encryption, final ModelMap model)
	{
		if (!DESedeEncryption.matchMACAddress(encryption))
		{
			model.addAttribute("authorizationID", DESedeEncryption.hecMAC());
			model.addAttribute("authorizeFail", Boolean.valueOf(true));
			return "/admin/systemConfig/authorize";
		}
		systemConfigService.updateEncryptionString(encryption);
		AuthenticationRealm.isAuthorized = true;
		return "redirect:/admin";
	}
}