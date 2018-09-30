package com.financial.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtils {

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param inputStream
	 * @return Map<String, String>
	 * @throws IOException
	 * @throws DocumentException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(InputStream inputStream) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();

			// 遍历所有子节点
			for (Element e : elementList)
				map.put(e.getName(), e.getText());
		} finally {
			// 释放资源
			if (inputStream != null)
				inputStream.close();
			inputStream = null;

		}

		return map;
	}

	/**
	 * 对象转换成xml
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static <T> String messageToXml(String root, T message) {
		StringBuilder builder = new StringBuilder();
		builder.append("<").append(root).append(">");

		messageToXml(message.getClass(), message, builder);

		builder.append("</").append(root).append(">");
		return builder.toString();
	}

	public static <T> void messageToXml(Class<?> cls, T message, StringBuilder builder) {
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			Class<?> clazz = field.getType();
			String fieldName = field.getName();
			Object fieldVal;
			try {
				field.setAccessible(true);
				fieldVal = field.get(message);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				continue;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
			if (fieldVal == null)
				continue;

			if (clazz.isAssignableFrom(List.class))
				builder.append(messageToXml(fieldName, (List<?>) fieldVal));
			else {
				builder.append("<").append(fieldName).append(">");
				if (clazz.isAssignableFrom(String.class))
					builder.append("<![CDATA[").append(fieldVal).append("]]>");
				else
					builder.append(fieldVal);
				builder.append("</").append(fieldName).append(">");
			}
		}

		Class<?> superClass = cls.getSuperclass();
		if (superClass != null && superClass != Object.class)
			messageToXml(superClass, message, builder);

	}

	/**
	 * 对象转换成xml
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	private static <T> String messageToXml(String root, List<T> message) {
		StringBuilder builder = new StringBuilder();
		builder.append("<").append(root).append(">");

		for (T t : message) {
			messageToXml("List", t);
		}

		builder.append("</").append(root).append(">");
		return builder.toString();
	}

}
