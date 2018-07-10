package com.financial.files.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.financial.common.controller.BaseController;
import com.financial.common.file.FileUtils;
import com.financial.files.model.FileInfo;
import com.financial.files.service.FileInfoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("files")
@Slf4j
public class FilesController extends BaseController {

	@Value("${files.path}")
	private String filePath;

	@Autowired
	private FileInfoService fileInfoService;

	@PostMapping(MAPPING_URL.ALL)
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response) {
		log.debug("------------------> 上传文件 begin <------------------");
		if (!(request instanceof MultipartHttpServletRequest)) {
			return "请求中不存在文件";
		}

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		if (files == null || files.size() == 0)
			return "请求中不存在文件";

		String path = request.getRequestURI().replaceFirst("/files", "");// 获取存储目录
		path = filePath + path;
		if (!path.endsWith("/"))
			path += "/";

		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) // 目录不存在创建目录
			file.mkdirs();

		for (MultipartFile multipartFile : files) {
			OutputStream os = null;
			try {
				String originalFilename = multipartFile.getOriginalFilename();
				String pathName = new StringBuilder(path).append(UUID.randomUUID().toString())
						.append(originalFilename.substring(originalFilename.lastIndexOf("."))).toString();
				log.info("写入文件:[{}], 文件大小:[{}], 文件位置:[{}]", multipartFile.getOriginalFilename(),
						multipartFile.getSize(), pathName);
				os = new FileOutputStream(new File(pathName));
				os.write(multipartFile.getBytes());
				os.flush();

				fileInfoService.addFileInfo(multipartFile, pathName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				FileUtils.close(null, os);
			}
		}

		log.debug("------------------> 上传文件 end <------------------");
		return "success!";

	}

	@GetMapping(value = { MAPPING_URL.TOKEN })
	public void online(HttpServletRequest request, HttpServletResponse response, @PathVariable("token") String token) {
		// 根据 token 查询 文件路径
		FileInfo fileInfo = fileInfoService.getFileInfoByToken(token);
		if (fileInfo != null) {
			download(response, fileInfo.getPathName(), fileInfo.getFileName(), "inline; ");
			return;
		}
		// token不存在
		online(request, response);
	}

	@GetMapping(value = { MAPPING_URL.ALL })
	public void online(HttpServletRequest request, HttpServletResponse response) {
		// 根据 request 获取文件路径
		String path = request.getRequestURI().replaceFirst("/files/", "");
		download(response, filePath + path, null, "inline; ");
	}

	@GetMapping(value = { MAPPING_URL.DOWN + MAPPING_URL.TOKEN })
	public void download(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("token") String token) {
		// 根据 token 查询 文件路径
		FileInfo fileInfo = fileInfoService.getFileInfoByToken(token);
		if (fileInfo != null) {
			download(response, fileInfo.getPathName(), fileInfo.getFileName(), "attachment; ");
			return;
		}
		// token不存在
		download(request, response);
	}

	@GetMapping(value = { MAPPING_URL.DOWN + MAPPING_URL.ALL })
	public void download(HttpServletRequest request, HttpServletResponse response) {
		// 根据 request 获取文件路径
		String path = request.getRequestURI().replaceFirst("/files/down/", "");
		boolean res = download(response, filePath + path, null, "attachment; ");
		if (!res)
			online(request, response);

	}

	/** 具体文件下载 */
	private boolean download(HttpServletResponse response, String pathName, String fileName, String type) {
		log.debug("---------------> 下载文件 begin  <---------------");
		log.debug("文件路径: [{}], 文件名称: [{}], 文件下载在线打开标志： [{}]", pathName, fileName, type);
		if (fileName == null)// 解析文件名称
			fileName = pathName.substring(pathName.lastIndexOf("/") + 1);
		try {
			fileName = new String(fileName.getBytes(), "ISO-8859-1");// 编码文件名称
		} catch (UnsupportedEncodingException e) {
			log.error("文件名称:[ " + fileName + " ] 编码失败, " + e.getMessage(), e);
		}

		InputStream is = null;
		OutputStream os = null;
		File file = new File(pathName);
		if (!file.exists() && "attachment; ".equals(type))
			return false;
		Exception error = null;
		try {
			os = response.getOutputStream();
			is = new FileInputStream(file);
			response.setHeader("Content-Disposition",
					new StringBuilder().append(type).append(" filename=\"").append(fileName).append("\"").toString());
			if ("inline; ".equals(type))
				response.setContentType(file.toURI().toURL().openConnection().getContentType());
			else
				response.setContentType("application/x-msdownload");

			FileUtils.isToOs(is, os);// 输入流中数据写入输出流
			log.debug("---------------> 下载文件 end  <---------------");
		} catch (FileNotFoundException e) {
			error = e;
			log.error("文件不存在: " + e.getMessage(), e);
		} catch (IOException e) {
			error = e;
			log.error("文件输出异常: " + e.getMessage(), e);
		}

		if (error != null) {
			response.setContentType("text/html;charset=utf-8");
			FileUtils.outputMessage(os, error.getMessage());
		}

		FileUtils.close(is, os);
		return true;
	}
}
