package net.eshop.encryption;

import java.math.BigInteger;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class DESedeEncryption
{

	private static final String Algorithm = "DESede"; // 定义 加密算法,可用
	// DES,DESede,Blowfish

	// keybyte为加密密钥，长度为24字节
	static final byte[] keyBytes =
	{ 0x11, 0x22, 0x4F, 0x58,

	(byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51,

	(byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74,

	(byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2

	}; // 24字节的密钥

	// src为被加密的数据缓冲区（源）

	private static byte[] encryptMode(final byte[] src)
	{
		final com.sun.crypto.provider.SunJCE sunJCE = new com.sun.crypto.provider.SunJCE();
		Security.addProvider(sunJCE);
		try
		{
			// 生成密钥
			final SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			// 加密
			final Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		}
		catch (final java.security.NoSuchAlgorithmException e1)
		{
			e1.printStackTrace();
		}
		catch (final javax.crypto.NoSuchPaddingException e2)
		{
			e2.printStackTrace();
		}
		catch (final java.lang.Exception e3)
		{
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 先将十六进制的MAC地址转换成byte[] 然后加密。
	 *
	 * @param hecMAC
	 *           十六进制的MAC地址
	 * @return 加密后转成十六进制
	 */
	public static String encryptToHecString(final String hecMAC)
	{
		final byte[] macBytes = new BigInteger(hecMAC, 16).toByteArray();
		//		System.out.println("encryptToHecString MAC Address:" + new String(macBytes));
		return byte2hex(encryptMode(macBytes));
	}


	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区

	private static byte[] decryptMode(final byte[] src)
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try
		{
			// 生成密钥
			final SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			// 解密
			final Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		}
		catch (final java.security.NoSuchAlgorithmException e1)
		{
			e1.printStackTrace();
		}
		catch (final javax.crypto.NoSuchPaddingException e2)
		{
			e2.printStackTrace();
		}
		catch (final java.lang.Exception e3)
		{
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 利用 keyBytes 作为秘钥解密十六进制串 to 原始明文字符串
	 *
	 * @param encryptedHecString
	 * @param keyBytes
	 * @return 解密后的明文
	 */
	public static String decryptHecString(final String encryptedHecString)
	{
		final byte[] macBytes = new BigInteger(encryptedHecString, 16).toByteArray();
		final byte[] srcBytes = decryptMode(macBytes);
		return new String(srcBytes);
	}





	/**
	 * 字节组转换成十六进制串
	 *
	 * @param b
	 * @return 十六进制串
	 */
	private static String byte2hex(final byte[] b)
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
			{
				hs = hs + "0" + stmp;
			}
			else
			{
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * 转换成十六进制数。
	 *
	 * @return 十六进制串
	 * @throws Exception
	 */
	public static String hecMAC()
	{
		try
		{
			final String MAC = MACTool.getMAC();
			return byte2hex(MAC.getBytes());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static void main(final String[] args) throws Exception
	{
		//combineMACToKey(MACTool.getMAC());
		// 添加新安全算法,如果用JCE就要把它添加进去
		System.out.println("加密前MAC地址:" + MACTool.getMAC());
		final String hecMac = hecMAC();
		System.out.println("十六进制MAC 地址：" + hecMac);
		final String encyptedHecMAC = encryptToHecString(hecMac);
		System.out.println("加密后的十六进制MAC地址:" + encyptedHecMAC);
		System.out.println("解密后的字符串:" + decryptHecString(encyptedHecMAC));
	}

	/**
	 * Verify if it match with the MAC Address
	 *
	 * @param encryptedHex
	 */
	public static boolean matchMACAddress(final String encryptedHex)
	{
		String MAC;
		try
		{
			MAC = MACTool.getMAC();
			if (MAC != null && MAC.equals(decryptHecString(encryptedHex)))
			{
				return true;
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return false;

	}
}