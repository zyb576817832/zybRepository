/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.ebon.framework.util;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

public class StringUtil {
	public static boolean isNotNull(String str) {
		boolean b = false;
		if ((str != null) && (str.trim().length() > 0)
				&& (!(str.equalsIgnoreCase("null")))
				&& (!(str.equals("undefined")))) {
			b = true;
		}
		return b;
	}

	public static boolean isNotNull(Map<?, ?> map) {
		return ((map != null) && (!(map.isEmpty())));
	}

	public static <T> boolean isNotNull(List<T> list) {
		boolean b = false;
		if ((list != null) && (list.size() > 0)) {
			b = true;
		}
		return b;
	}

	public static boolean isNotNull(int str) {
		return (str != 0);
	}

	public static boolean isNotNull(StringBuffer str) {
		return ((str != null) && (!("".equalsIgnoreCase(str.toString()))) && (str
				.toString().trim().length() >= 1));
	}

	public static boolean isNotNull(String[] str) {
		return ((str != null) && (str.length != 0));
	}

	public static boolean isNotNull(Object obj) {
		boolean b = false;
		if (obj != null) {
			b = true;
		}
		return b;
	}

	public static String toString(Object obj) {
		String str = null;
		if (isNotNull(obj))
			str = String.valueOf(obj).trim();
		else {
			str = "";
		}
		return str;
	}

	public static String toString(int str) {
		return String.valueOf(str);
	}

	public static String toString(String str) {
		return ((str != null) ? str.trim() : "");
	}

	public static String toString(BigDecimal str) {
		return ((str != null) ? str.toString() : "");
	}

	public static int getIntValue(String v) {
		return getIntValue(v, -1);
	}

	public static int getIntValue(String v, int def) {
		try {
			return Integer.parseInt(v.trim());
		} catch (Exception ex) {
		}
		return def;
	}

	public static String formatStrGBK(String str) throws Exception {
		String result = "";
		if (isNotNull(str)) {
			result = new String(str.getBytes("ISO-8859-1"), "GBK");
		}
		return result.trim();
	}

	public static String formatStrUTF(String str) throws Exception {
		String result = "";
		if (isNotNull(str)) {
			result = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		}
		return result.trim();
	}

	public static String formatStrUTF(StringBuilder str) throws Exception {
		String result = "";
		if (isNotNull(str)) {
			result = new String(str.toString().getBytes("ISO-8859-1"), "UTF-8");
		}
		return result.trim();
	}

	public static String formatHTML(String str) {
		return ((str != null) ? str.trim().replace("<", "&lt;")
				.replace(">", "&gt;").replace("\"", "&quot;") : "");
	}

	public static String pluralize(String var1) {
		if (var1.substring(var1.length() - 1).matches("[A-Z]"))
			return var1;
		if (var1.endsWith("y")) {
			if ((var1.endsWith("ay")) || (var1.endsWith("ey"))
					|| (var1.endsWith("oy")) || (var1.endsWith("uy"))) {
				return var1 + "s";
			}
			return var1.substring(0, var1.length() - 1) + "ies";
		}

		if ((var1.endsWith("a")) || (var1.endsWith("o"))
				|| (var1.endsWith("u"))) {
			return var1 + "es";
		}
		return var1 + "s";
	}

	public static String encrypt(String str) throws Exception {
		StringBuffer sb = new StringBuffer(32);
		String base64Str = new String(Base64.encodeBase64(str.getBytes()));
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(base64Str.getBytes("utf-8"));

		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString(array[i] & 0xFF | 0x100)
					.toUpperCase().substring(1, 3));
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(encrypt("admin"));
	}

	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf("/");
		return ((separatorIndex != -1) ? path.substring(separatorIndex + 1)
				: path);
	}

	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int extIndex = path.lastIndexOf(46);
		if (extIndex == -1) {
			return null;
		}
		int folderIndex = path.lastIndexOf("/");
		if (folderIndex > extIndex) {
			return null;
		}
		return path.substring(extIndex + 1);
	}

	public static String getFilPath(String path) {
		if (path == null) {
			return null;
		}
		path = path.replaceAll("\\\\", "/");
		int separatorIndex = path.lastIndexOf("/");
		return ((separatorIndex != -1) ? path.substring(0, separatorIndex + 1)
				: path);
	}

	public static String getFilenameNoExtension(String path) {
		if (path == null) {
			return null;
		}
		int extIndex = path.lastIndexOf(46);
		if (extIndex == -1) {
			return path;
		}
		int folderIndex = path.lastIndexOf("/");
		if (folderIndex > extIndex) {
			return path;
		}
		return path.substring(folderIndex + 1, extIndex);
	}

	public static <T> T[] zeroSizeToNull(T[] array) {
		if ((array != null) && (array.length == 0)) {
			return null;
		}

		return array;
	}

	public static <T> List<T> zeroSizeToNull(List<T> list) {
		if ((list != null) && (list.size() == 0)) {
			return null;
		}

		return list;
	}
}