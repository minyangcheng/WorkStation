package com.example.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ByteUtils {

	public static void main(String args[]) {

		byte[] b = hexStringToBytes("25 52 44 4d 45 54 45 52".replace(" ", ""));
		System.out.println(bytesToAsciiString(b));
	}

	/**
	 * 鍚堝苟byte[]
	 * 
	 * @param srcArrays
	 * @return
	 */
	public static byte[] streamCopy(List<byte[]> srcArrays) {
		byte[] destAray = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			for (byte[] srcArray : srcArrays) {
				bos.write(srcArray);
			}
			bos.flush();
			destAray = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
			}
		}
		return destAray;
	}

	/**
	 * 鍙嶈浆byte[]
	 * 
	 * @param bs
	 * @return
	 */
	public static byte[] invertBytes(byte[] bs) {
		int len = bs.length;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[len - (1 + i)] = bs[i];
		}
		return result;
	}

	/**
	 * 鎸囧畾闀垮害宸﹁ˉ0
	 * 
	 * @param s
	 * @param len
	 * @return
	 */
	public static String addLeftZero(String s, int len) {
		int tempLen = s.length();
		StringBuffer sbuffer = new StringBuffer();
		if (tempLen < len) {
			int temp = len - tempLen;
			for (int i = 0; i < temp; i++) {
				sbuffer.append("0");
			}
		}
		sbuffer.append(s);
		return sbuffer.toString();
	}

	/**
	 * 鎸囧畾闀垮害鍙宠ˉ0
	 * 
	 * @param s
	 * @param len
	 * @return
	 */
	public static String addRightZero(String s, int len) {
		int tempLen = s.length();
		StringBuffer sbuffer = new StringBuffer(s);
		if (tempLen < len) {
			int temp = len - tempLen;
			for (int i = 0; i < temp; i++) {
				sbuffer.append("0");
			}
		}

		return sbuffer.toString();
	}

	/**
	 * byte[]杞�16杩涘埗�?�楃涓�?
	 * 
	 * @param bytes
	 * @param isSpace
	 *            16杩涘埗�?�楃涓蹭箣闂存槸鍚�?姞绌烘牸
	 * @return
	 */
	public static String bytesToHexString(byte[] bytes, boolean isSpace) {
		StringBuffer result = new StringBuffer();
		String upByte, downByte;
		for (int i = 0; i < bytes.length; i++) {
			upByte = Integer.toHexString((bytes[i] & 0xF0) >> 4);
			downByte = Integer.toHexString(bytes[i] & 0xF);
			result.append(upByte).append(downByte);
			if (isSpace) {
				result.append(" ");
			}
		}
		return result.toString().toLowerCase().trim();
	}

	/**
	 * 涓�涓瓧鑺俠yte杞�16杩涘埗�?�楃涓�?
	 * 
	 * @param b
	 * @return
	 */
	public static String oneByteToHexString(byte b) {
		byte[] bytes = new byte[1];
		bytes[0] = b;
		return bytesToHexString(bytes, false);
	}

	/**
	 * byte[]杞寲涓�?16杩涘埗�?�楃涓�?
	 * 
	 * @param bytes
	 * @param offset
	 *            瑕佽浆鍖栧瓧鑺傜殑璧峰浣嶇�?
	 * @param len
	 *            瑕佽浆鍖栧瓧鑺傜殑闀垮害
	 * @return
	 */
	public static String bytesToHexString(byte[] bytes, int offset, int len) {
		StringBuffer result = new StringBuffer();
		String upByte, downByte;
		for (int i = offset; i < offset + len; i++) {
			upByte = Integer.toHexString((bytes[i] & 0xF0) >> 4);
			downByte = Integer.toHexString(bytes[i] & 0xF);
			result.append(upByte).append(downByte);
		}
		return result.toString().toLowerCase();
	}

	/**
	 * 16杩涘埗�?�楃涓茶浆byte[]
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] hexStringToBytes(String value) {
		value = value.replace(" ", "");
		byte[] bytes = null;
		StringBuffer valueBuf = new StringBuffer();
		if (value.length() % 2 == 1) {
			valueBuf.append("0");
		}
		valueBuf.append(value);
		char[] values = valueBuf.toString().toCharArray();

		int len = values.length / 2;
		bytes = new byte[len];
		byte upByte, downByte;
		for (int i = 0; i < values.length; i += 2) {
			upByte = Byte.decode("0x" + String.valueOf(values[i]));
			downByte = Byte.decode("0x" + String.valueOf(values[i + 1]));
			bytes[i / 2] = (byte) ((upByte << 4) | downByte);
		}
		return bytes;
	}

	/**
	 * 16杩涘埗�?�楃涓茶浆涓�涓瓧鑺俠yte
	 * 
	 * @param value
	 * @return
	 */
	public static byte hexStringToOneByte(String value) {
		int len = value.length();
		if (len > 2) {
			value = value.substring(0, 2);
		} else if (len < 2) {
			value = addLeftZero(value, 2);
		}
		byte[] bytes = hexStringToBytes(value);
		return bytes[0];
	}

	/**
	 * 褰撳墠鏃堕棿褰㈠紡鐨勫勾鏈堟棩鏃跺垎绉掕浆byte[]
	 * 
	 * @return
	 */
	public static byte[] getNowTimeByte() {
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String nowStr = format.format(new Date());
		return hexStringToBytes(nowStr);
	}

	/**
	 * 16杩涘埗�?�楃涓茶浆鍖栦负浣庝綅鍦ㄥ墠鐨刡yte[]
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] hexStringToInvertBytes(String value) {
		byte[] dataBytes = null;
		String tempStr = "";
		if (value.length() % 2 == 1) {
			tempStr = "0";
		}
		tempStr = tempStr + value;
		dataBytes = ByteUtils.hexStringToBytes(tempStr);
		int len = dataBytes.length;
		byte[] retBytes = new byte[len];
		int index = 0;
		while (index < len) {
			retBytes[index] = dataBytes[len - 1 - index];
			index++;
		}
		return retBytes;
	}

	/**
	 * 浣庝綅鍦ㄥ墠鐨刡yte[]杞寲涓�?16杩涘埗�?�楃涓�?
	 * 
	 * @param value
	 * @return
	 */
	public static String invertBytesTohexString(byte[] value) {
		int index = 0;
		int len = value.length;
		String retStr = "";
		String tempStr = null;
		while (index < len) {
			tempStr = ByteUtils.bytesToHexString(value, index, 1);
			retStr = tempStr + retStr;
			index++;
		}
		return retStr;
	}

	/**
	 * ascii鐮乥yte[]杞瑂tring
	 * 
	 * @param value
	 * @return
	 */
	public static String bytesToAsciiString(byte[] value) {
		try {
			return new String(value, "ascii");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * string杞负ascii鐮乥yte[]
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] asciiStringToBytes(String value) {
		try {
			byte[] result = value.getBytes("ascii");
			return result;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
