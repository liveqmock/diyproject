/*
 *
 *
 *
 */
package net.eshop;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.eshop.Setting.AccountLockType;
import net.eshop.Setting.CaptchaType;
import net.eshop.encryption.UnAuthorizedUsageOfSoftware;
import net.eshop.entity.Admin;
import net.eshop.service.AdminService;
import net.eshop.service.CaptchaService;
import net.eshop.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


/**
 * 权限认证
 *
 *
 *
 */
public class AuthenticationRealm extends AuthorizingRealm
{

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	public static boolean isAuthorized = false;

	/**
	 * 获取认证信息
	 *
	 * @param token
	 *           令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final org.apache.shiro.authc.AuthenticationToken token)
	{
		if (!isAuthorized)
		{
			throw new UnAuthorizedUsageOfSoftware(
					"You are not authorized to use this software. Please contact with the vendor of this software immediately!");
		}
		final AuthenticationToken authenticationToken = (AuthenticationToken) token;
		final String username = authenticationToken.getUsername();
		final String password = new String(authenticationToken.getPassword());
		final String captchaId = authenticationToken.getCaptchaId();
		final String captcha = authenticationToken.getCaptcha();
		final String ip = authenticationToken.getHost();
		if (!captchaService.isValid(CaptchaType.adminLogin, captchaId, captcha))
		{
			throw new UnsupportedTokenException();
		}
		if (username != null)
		{
			final Admin admin = adminService.findByUsername(username);
			if (admin == null)
			{
				throw new UnknownAccountException();
			}
			if (!admin.getIsEnabled())
			{
				throw new DisabledAccountException();
			}
			final Setting setting = SettingUtils.get();
			if (admin.getIsLocked())
			{
				if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin))
				{
					final int loginFailureLockTime = setting.getAccountLockTime();
					if (loginFailureLockTime == 0)
					{
						throw new LockedAccountException();
					}
					final Date lockedDate = admin.getLockedDate();
					final Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					if (new Date().after(unlockDate))
					{
						admin.setLoginFailureCount(0);
						admin.setIsLocked(false);
						admin.setLockedDate(null);
						adminService.update(admin);
					}
					else
					{
						throw new LockedAccountException();
					}
				}
				else
				{
					admin.setLoginFailureCount(0);
					admin.setIsLocked(false);
					admin.setLockedDate(null);
					adminService.update(admin);
				}
			}
			if (!DigestUtils.md5Hex(password).equals(admin.getPassword()))
			{
				final int loginFailureCount = admin.getLoginFailureCount() + 1;
				if (loginFailureCount >= setting.getAccountLockCount())
				{
					admin.setIsLocked(true);
					admin.setLockedDate(new Date());
				}
				admin.setLoginFailureCount(loginFailureCount);
				adminService.update(admin);
				throw new IncorrectCredentialsException();
			}
			admin.setLoginIp(ip);
			admin.setLoginDate(new Date());
			admin.setLoginFailureCount(0);
			adminService.update(admin);
			return new SimpleAuthenticationInfo(new Principal(admin.getId(), username), password, getName());
		}
		throw new UnknownAccountException();
	}

	/**
	 * 获取授权信息
	 *
	 * @param principals
	 *           principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals)
	{
		final Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null)
		{
			final List<String> authorities = adminService.findAuthorities(principal.getId());
			if (authorities != null)
			{
				final SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermissions(authorities);
				return authorizationInfo;
			}
		}
		return null;
	}



}