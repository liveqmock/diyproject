/*
 *
 *
 *
 */
package net.eshop.plugin.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.eshop.FileInfo;
import net.eshop.entity.PluginConfig;
import net.eshop.plugin.StoragePlugin;
import net.eshop.util.SettingUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;


/**
 * Plugin - FTP
 *
 *
 *
 */
@Component("ftpPlugin")
public class FtpPlugin extends StoragePlugin
{

	@Override
	public String getName()
	{
		return "FTP存储";
	}

	@Override
	public String getVersion()
	{
		return "1.0";
	}

	@Override
	public String getAuthor()
	{
		return SettingUtils.get().getSiteName();
	}

	@Override
	public String getSiteUrl()
	{
		return SettingUtils.get().getSiteUrl();
	}

	@Override
	public String getInstallUrl()
	{
		return "ftp/install.jhtml";
	}

	@Override
	public String getUninstallUrl()
	{
		return "ftp/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl()
	{
		return "ftp/setting.jhtml";
	}

	@Override
	public void upload(final String path, final File file, final String contentType)
	{
		final PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null)
		{
			final String host = pluginConfig.getAttribute("host");
			final Integer port = Integer.valueOf(pluginConfig.getAttribute("port"));
			final String username = pluginConfig.getAttribute("username");
			final String password = pluginConfig.getAttribute("password");
			final FTPClient ftpClient = new FTPClient();
			InputStream inputStream = null;
			try
			{
				inputStream = new FileInputStream(file);
				ftpClient.connect(host, port);
				ftpClient.login(username, password);
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
				{
					final String directory = StringUtils.substringBeforeLast(path, "/");
					final String filename = StringUtils.substringAfterLast(path, "/");
					if (!ftpClient.changeWorkingDirectory(directory))
					{
						final String[] paths = StringUtils.split(directory, "/");
						String p = "/";
						ftpClient.changeWorkingDirectory(p);
						for (final String s : paths)
						{
							p += s + "/";
							if (!ftpClient.changeWorkingDirectory(p))
							{
								ftpClient.makeDirectory(s);
								ftpClient.changeWorkingDirectory(p);
							}
						}
					}
					ftpClient.storeFile(filename, inputStream);
					ftpClient.logout();
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				IOUtils.closeQuietly(inputStream);
				if (ftpClient.isConnected())
				{
					try
					{
						ftpClient.disconnect();
					}
					catch (final IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public String getUrl(final String path)
	{
		final PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null)
		{
			final String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			return urlPrefix + path;
		}
		return null;
	}

	@Override
	public List<FileInfo> browser(final String path)
	{
		final List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		final PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null)
		{
			final String host = pluginConfig.getAttribute("host");
			final Integer port = Integer.valueOf(pluginConfig.getAttribute("port"));
			final String username = pluginConfig.getAttribute("username");
			final String password = pluginConfig.getAttribute("password");
			final String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			final FTPClient ftpClient = new FTPClient();
			try
			{
				ftpClient.connect(host, port);
				ftpClient.login(username, password);
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()) && ftpClient.changeWorkingDirectory(path))
				{
					for (final FTPFile ftpFile : ftpClient.listFiles())
					{
						final FileInfo fileInfo = new FileInfo();
						fileInfo.setName(ftpFile.getName());
						fileInfo.setUrl(urlPrefix + path + ftpFile.getName());
						fileInfo.setIsDirectory(ftpFile.isDirectory());
						fileInfo.setSize(ftpFile.getSize());
						fileInfo.setLastModified(ftpFile.getTimestamp().getTime());
						fileInfos.add(fileInfo);
					}
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (ftpClient.isConnected())
				{
					try
					{
						ftpClient.disconnect();
					}
					catch (final IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return fileInfos;
	}

}