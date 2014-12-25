package com.joe.tools.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * 使用URLConnection下载文件或图片并保存到本地。
 *
 * 
 */
public class URLConnectionDownloader {
	public static void main(String[] args) throws Exception {
		String directory = "C:\\Joe\\DIY\\GITHUB\\DIYProjects\\EShop\\WebContent\\upload\\image\\201412\\";
		String directory2 = "C:\\temp\\";
		batchDownload("C:\\temp\\URLs.txt",directory2);
	}
	
	
	/**
	 * 顺序批量下载文件内部的URL。一行一个URL
	 * @param file
	 * @throws Exception
	 */
	public  static void batchDownload(String filePath,String directory) throws Exception{
		File file  = new File(filePath.trim());
		if (!file.exists()){
			throw new Exception("文件路径不正确");
		}
		File folder  = new File(directory.trim());
		if(folder.exists() && !folder.isDirectory()){
			throw new Exception("目标文件夹路径不正确");
		}
		if(!folder.exists()){
			folder.mkdir();
		}
		int i=0;
		List<String> list = FileUtils.readLines(file);
		for (String string : list) {
			System.out.println(i++);
			if(string.trim().equals("")){
				continue;
			}
			download(string.trim(), directory);
		}
	}

	/**
	 * 下载文件到本地
	 *
	 * @param urlString
	 *            被下载的文件地址
	 * @param filename
	 *            本地文件名
	 * @throws Exception
	 *             各种异常
	 */
	public static void download(String urlString, String directory)
			throws Exception {
		// 构造URL
		String filename = null;
		URL url = new URL(urlString);
		String[] ss = urlString.split("/");
		filename = directory+"\\"+ss[ss.length-1];
		
		// 打开连接
		URLConnection con = url.openConnection();
		// 输入流
		InputStream is = con.getInputStream();
		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		OutputStream os = new FileOutputStream(filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}
}