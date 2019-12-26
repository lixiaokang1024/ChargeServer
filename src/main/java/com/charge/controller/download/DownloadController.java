package com.charge.controller.download;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;

@Controller
public class DownloadController {

	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);
	
	@RequestMapping(value = "/download")
	public void download(HttpServletRequest request, HttpServletResponse response, String fileName, String filePath) {
		
		if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(filePath)) {
			return;
		}
		
		String templateFilePath = request.getSession().getServletContext().getRealPath("/");
		StringBuilder builder = new StringBuilder();
		builder.append(templateFilePath).append(File.separator).append(File.separator);
		File outPutFile = new File(builder.append(filePath).toString());
		
		ServletOutputStream outputStream = null;
		try {
			// String _fileName = URLDecoder.decode(fileName, "ISO8859-1");
			//设置文件名称
			byte[] datas = FileUtils.readFileToByteArray(outPutFile);
			if(logger.isDebugEnabled()) {
				logger.debug("delete temp file ["+outPutFile.getAbsolutePath()+"]");
			}
			FileUtils.deleteQuietly(outPutFile);
			
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes(), "ISO8859-1"));
			response.setCharacterEncoding("UTF-8");
			outputStream = response.getOutputStream();
			outputStream.write(datas);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
		
	}
	
}
