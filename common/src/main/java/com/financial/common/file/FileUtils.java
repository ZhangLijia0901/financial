package com.financial.common.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 文件处理工具类
 * 
 * @author: 张礼佳
 * @date: 2018年6月6日 上午10:09:39
 */
public class FileUtils {

	/**
	 * 将输入流中数据读取并写入到输出流
	 * 
	 * @param is 输入流
	 * @param os 输出流
	 * @throws IOException io流异常
	 */
	public static void isToOs(InputStream is, OutputStream os) throws IOException {

		int len = 0;
		byte[] bs = new byte[1024];

		while ((len = is.read(bs)) > 0) {
			os.write(bs, 0, len);
		}
	}

	/** 输出消息 */
	public static void outputMessage(OutputStream os, String message) {
		if (message == null || os == null)
			return;
		try {
			os.write(message.getBytes());
		} catch (IOException e) {
		}
	}

	/** 关闭流 */
	public static void close(InputStream is, OutputStream os) {
		if (is != null)
			try {
				is.close();
			} catch (IOException e) {
			}
		if (os != null)
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
			}

	}
}
