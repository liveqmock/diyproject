/*
 *
 *
 *
 */
package net.eshop.filter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.eshop.AuthenticationToken;
import net.eshop.service.RSAService;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;


/**
 * Filter - 权限认证
 *
 *
 *
 */
public class AuthenticationFilter extends FormAuthenticationFilter
{


	/** 默认"加密密码"参数名称 */
	private static final String DEFAULT_EN_PASSWORD_PARAM = "enPassword";

	/** 默认"验证ID"参数名称 */
	private static final String DEFAULT_CAPTCHA_ID_PARAM = "captchaId";

	/** 默认"验证码"参数名称 */
	private static final String DEFAULT_CAPTCHA_PARAM = "captcha";

	/** "加密密码"参数名称 */
	private String enPasswordParam = DEFAULT_EN_PASSWORD_PARAM;

	/** "验证ID"参数名称 */
	private String captchaIdParam = DEFAULT_CAPTCHA_ID_PARAM;

	/** "验证码"参数名称 */
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;



	@Override
	protected org.apache.shiro.authc.AuthenticationToken createToken(final ServletRequest servletRequest,
			final ServletResponse servletResponse)
	{

		final String username = getUsername(servletRequest);
		final String password = getPassword(servletRequest);
		final String captchaId = getCaptchaId(servletRequest);
		final String captcha = getCaptcha(servletRequest);
		final boolean rememberMe = isRememberMe(servletRequest);
		final String host = getHost(servletRequest);
		return new AuthenticationToken(username, password, captchaId, captcha, rememberMe, host);
	}



	@Override
	protected boolean onAccessDenied(final ServletRequest servletRequest, final ServletResponse servletResponse) throws Exception
	{
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest"))
		{
			response.addHeader("loginStatus", "accessDenied");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		return super.onAccessDenied(request, response);
	}

	@Override
	protected boolean onLoginSuccess(final org.apache.shiro.authc.AuthenticationToken token, final Subject subject,
			final ServletRequest servletRequest, final ServletResponse servletResponse) throws Exception
	{
		Session session = subject.getSession();
		final Map<Object, Object> attributes = new HashMap<Object, Object>();
		final Collection<Object> keys = session.getAttributeKeys();
		for (final Object key : keys)
		{
			attributes.put(key, session.getAttribute(key));
		}
		session.stop();
		session = subject.getSession();
		for (final Entry<Object, Object> entry : attributes.entrySet())
		{
			session.setAttribute(entry.getKey(), entry.getValue());
		}
		return super.onLoginSuccess(token, subject, servletRequest, servletResponse);
	}

	@Override
	protected String getPassword(final ServletRequest servletRequest)
	{
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final String password = rsaService.decryptParameter(enPasswordParam, request);
		rsaService.removePrivateKey(request);
		return password;
	}

	/**
	 * 获取验证ID
	 *
	 * @param servletRequest
	 *           ServletRequest
	 * @return 验证ID
	 */
	protected String getCaptchaId(final ServletRequest servletRequest)
	{
		String captchaId = WebUtils.getCleanParam(servletRequest, captchaIdParam);
		if (captchaId == null)
		{
			captchaId = ((HttpServletRequest) servletRequest).getSession().getId();
		}
		return captchaId;
	}

	/**
	 * 获取验证码
	 *
	 * @param servletRequest
	 *           ServletRequest
	 * @return 验证码
	 */
	protected String getCaptcha(final ServletRequest servletRequest)
	{
		return WebUtils.getCleanParam(servletRequest, captchaParam);
	}

	/**
	 * 获取"加密密码"参数名称
	 *
	 * @return "加密密码"参数名称
	 */
	public String getEnPasswordParam()
	{
		return enPasswordParam;
	}

	/**
	 * 设置"加密密码"参数名称
	 *
	 * @param enPasswordParam
	 *           "加密密码"参数名称
	 */
	public void setEnPasswordParam(final String enPasswordParam)
	{
		this.enPasswordParam = enPasswordParam;
	}

	/**
	 * 获取"验证ID"参数名称
	 *
	 * @return "验证ID"参数名称
	 */
	public String getCaptchaIdParam()
	{
		return captchaIdParam;
	}

	/**
	 * 设置"验证ID"参数名称
	 *
	 * @param captchaIdParam
	 *           "验证ID"参数名称
	 */
	public void setCaptchaIdParam(final String captchaIdParam)
	{
		this.captchaIdParam = captchaIdParam;
	}

	/**
	 * 获取"验证码"参数名称
	 *
	 * @return "验证码"参数名称
	 */
	public String getCaptchaParam()
	{
		return captchaParam;
	}

	/**
	 * 设置"验证码"参数名称
	 *
	 * @param captchaParam
	 *           "验证码"参数名称
	 */
	public void setCaptchaParam(final String captchaParam)
	{
		this.captchaParam = captchaParam;
	}

}