/**
 *
 */
package net.eshop.encryption;

import org.apache.shiro.authc.AuthenticationException;


/**
 * @author joeli2
 *
 */
public class UnAuthorizedUsageOfSoftware extends AuthenticationException
{

	public UnAuthorizedUsageOfSoftware(final String msg)
	{

		super(msg);

	}
}
