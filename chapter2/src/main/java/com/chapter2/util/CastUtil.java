package com.chapter2.util;


public final class CastUtil {
	public static String castString(Object obj) {
		return castString(obj, "");
	}

	public static String castString(Object obj, String defaultValue) {
		return obj != null ? String.valueOf(obj) : defaultValue;
	}

	public static double castDouble(Object obj, double defaultValue) {
		double doubleValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					doubleValue = Double.parseDouble(strValue);
				} catch (NumberFormatException e) {

				}
			}
		}
		return doubleValue;
	}

	public static double castDouble(Object obj) {
		return CastUtil.castDouble(obj, 0);
	}

	public static long castLong(Object obj) {
		return CastUtil.castLong(obj, 0);
	}

	public static long castLong(Object obj, long defaultValue) {
		long longValue = defaultValue;
		if (obj != null) {
			String strValue = CastUtil.castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					longValue = Long.parseLong(strValue);
				} catch (NumberFormatException e) {

				}
			}
		}
		return longValue;
	}

	public static int castInt(Object obj, int defaultValue) {
		int intValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					intValue = Integer.parseInt(strValue);
				} catch (NumberFormatException e) {

				}
			}
		}
		return intValue;
	}

	public static int castInt(Object obj) {
		return castInt(obj, 0);
	}

	public static boolean castBoolean(Object obj, boolean defaultValue) {
		boolean booleanValue = defaultValue;
		if (obj != null) {
			booleanValue = Boolean.parseBoolean(castString(obj));
		}
		return booleanValue;
	}

	public static boolean castBoolean(Object obj) {
		return CastUtil.castBoolean(obj, false);
	}
}
