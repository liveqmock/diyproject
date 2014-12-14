package com.joe.tools.file;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * ʹ��URLConnection�����ļ���ͼƬ�����浽���ء�
 *
 * @author ������(laozizhu.com)
 */
public class URLConnectionDownloader {
	public static void main(String[] args) throws Exception {
		download(
				"http://storage.shopxx.net/demo-image/3.0/201301/3247296d-f02a-4a44-9d0d-f3343c7d7ddc.jpg",
				null);
	}

	/**
	 * �����ļ�������
	 *
	 * @param urlString
	 *            �����ص��ļ���ַ
	 * @param filename
	 *            �����ļ���
	 * @throws Exception
	 *             �����쳣
	 */
	public static void download(String urlString, String fileNameStr)
			throws Exception {
		// ����URL
		String filename = null;
		URL url = new URL(urlString);
		if (fileNameStr == null) {
			int index = urlString.indexOf("/201301/");
			filename = urlString.substring(index+8);
			filename = "C:\\Joe\\DIY\\L3\\code\\trunk\\Project\\EShop\\WebContent\\upload\\" + filename;
		}
		// ������
		URLConnection con = url.openConnection();
		// ������
		InputStream is = con.getInputStream();
		// 1K�����ݻ���
		byte[] bs = new byte[1024];
		// ��ȡ�������ݳ���
		int len;
		// ������ļ���
		OutputStream os = new FileOutputStream(filename);
		// ��ʼ��ȡ
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// ��ϣ��ر���������
		os.close();
		is.close();
	}
}