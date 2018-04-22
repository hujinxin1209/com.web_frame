package com.chapter2.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class PropsUtil {
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PropsUtil.class);
	private static Properties properties;

	private PropsUtil() {

	}

	public static Properties loadProps(String fileName) {
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (is == null)
				throw new FileNotFoundException(fileName + "file is not found");
			if (properties == null) {
				properties = new Properties();
				properties.load(is);
			}
		} catch (IOException e) {
			((org.slf4j.Logger) LOGGER).error("load properties file failure", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOGGER.error("close input stream failure", e);
				}
			}
		}
		return properties;
	}

	public static String getString(Properties properties, String key) {
		return getString(properties, key, "");
	}

	public static String getString(Properties properties, String key, String defaultValue) {
		String value = defaultValue;
		if (properties.containsKey(key)) {
			value = properties.getProperty(key);
		}
		return value;
	}

	public static int getInt(Properties properties, String key, int defaultValue) {
		int value = defaultValue;
		if (properties.containsKey(key)) {
			value = CastUtil.castInt(properties.getProperty(key));
		}
		return value;
	}

	public static boolean getBoolean(Properties properties, String key) {
		return getBoolean(properties, key, false);
	}

	public static boolean getBoolean(Properties properties, String key, Boolean defaultValue) {
		boolean value = defaultValue;
		if (properties.containsKey(key)) {
			value = CastUtil.castBoolean(properties.getProperty(key));
		}
		return value;
	}
}
