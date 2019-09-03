package com.flycode.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private StringUtil() {
	}

	/**
	 * NULL字符串转换，参数对象为null时，返回空字符串
	 * 
	 * @param o 转换原对象
	 * @return 字符串
	 */
	public static String nvl(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString().trim();
	}
	
	/**
	 * NULL、空字符串转换，参数对象为null或空字符串时，返回指定的字符串
	 * 
	 * @param o 转换原对象
	 * @param specified 指定的字符串
	 * @return 字符串
	 */
	public static String nvlReplace(Object o, String specified) {
		if (o == null || "".equals(o)) {
			return specified;
		}
		return o.toString().trim();
	}

	/**
	 * NULL字符串转换，参数对象为null时，返回字符串"0"
	 * 
	 * @param o 转换原对象
	 * @return 字符串
	 */
	public static String nvlnum(Object o) {
		if (o == null || "".equals(o.toString().trim())) {
			return "0";
		}
		return o.toString().trim();
	}

	/**
	 * NULL字符串转换，参数对象为null时，返回默认值
	 * 
	 * @param obj 转换原对象
	 * @param defaultStr 默认值
	 * @return 字符串
	 */
	public static String nvl(Object obj, String defaultStr) {
		if (obj == null) {
			return defaultStr;
		}
		return obj.toString().trim();
	}

	/**
	 * NULL字符串转换，参数对象为null或全部空格时，返回默认值
	 * 
	 * @param obj 转换原对象
	 * @param defaultStr 默认值
	 * @return 字符串
	 */
	public static String nvlspace(Object obj, String defaultStr) {
		if (obj == null || "".equals(obj.toString().trim())) {
			return defaultStr;
		}
		return obj.toString().trim();
	}

	/**
	 * 检查输入的字符串是否为（Null/""/" ")
	 * 
	 * @param src String 被检查的字符串
	 * @return tru/false
	 */
	public static boolean isNull(Object src) {
		if ((src == null) || ("").equals(src.toString().trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查输入的字符串是否不为（Null/""/" ")
	 * 
	 * @param src String 被检查的字符串
	 * @return tru/false
	 */
	public static boolean isNotNull(Object src) {
		return !isNull(src);
	}

	/**
	 * 输入的字符串是否全为半角
	 * 
	 * @param src
	 * @return 是true;否false
	 */
	public static boolean isAllHalfWidthString(String src) {
		boolean result = true;

		for (int i = 0; (src != null) && (i < src.length()); i++) {
			char c = src.charAt(i);

			if (isHalfWidth(c)) {
				if ((c == '&') || (c == '<') || (c == '>') || (c == '"') || (c == '\'')) {
					result = false;

					break;
				} else {
					continue;
				}
			} else {
				result = false;

				break;
			}
		}

		return result;
	}

	/**
	 * 输入的字符是否为半角
	 *
	 * @param c 被检查的字符
	 * @return 半角:true,全角:false
	 */
	public static boolean isHalfWidth(char c) {
		boolean result = true;

		// Get the Unicode block containing the given character
		Character.UnicodeBlock cub = Character.UnicodeBlock.of(c);

		if (cub.equals(Character.UnicodeBlock.BASIC_LATIN)) {
			// Basic Latin
			result = true;
		} else if (cub.equals(Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
			// Halfwidth and Fullwidth Forms
			int type = Character.getType(c);

			if ((type == Character.MODIFIER_LETTER) || (type == Character.OTHER_LETTER)) {
				// MODIFIER_LETTER , OTHER_LETTER
				result = true;
			} else {
				result = false;
			}
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * 检查传入的char是否是字母
	 *
	 * @param ch 被检查的char
	 * @return 字母:true.其它:false
	 */
	private static boolean
	_isAlpha(char ch) {
		return ((ch >= 'A') && (ch <= 'Z')) || ((ch >= 'a') && (ch <= 'z'));
	}

	/**
	 * 检查传入的char是否是数字
	 *
	 * @param ch 被检查的char
	 * @return 数字:true.其它:false
	 */
	private static boolean _isNumber(char ch) {
		return ((ch >= '0') && (ch <= '9'));
	}

	/**
	 * 检查字符串是否是数字或字母或_
	 *
	 * @param str 被检查的字符串
	 * @return 全为字符或数字或  class="ellipsis"    href=：true,存在非字符或数字或-的字符:false
	 */
	public static boolean isAlphaNumberMinus(String str) {
		if (isNull(str)) {
			return false;
		}

		char[] chArray = str.toCharArray();

		for (int index = 0; index < chArray.length; index++) {
			if (!_isAlpha(chArray[index]) && !_isNumber(chArray[index]) && !('_' == chArray[index])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 检查字符串是否是数字或字母
	 *
	 * @param str 被检查的字符串
	 * @return 全为字符或数字：true,存在非字符或数字的字符:false
	 */
	public static boolean isAlphaNumber(String str) {
		if (!isNull(str)) {
			return false;
		}

		char[] chArray = str.toCharArray();

		for (int index = 0; index < chArray.length; index++) {
			if (!_isAlpha(chArray[index]) && !_isNumber(chArray[index])) {
				return false;
			}
		}

		return true;
	}
	/**检查字符串是否是email格式
	 * @param str 被检查的字符串
	 * @return 如果是符合邮箱格式的字符串,返回<b>true</b>,否则为<b>false</b>
	 */
	public static boolean isEmail( String str ) {
		String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$" ;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher( str );
		return matcher.matches();
	}
	/**
	 * 查找字符串
	 * 
	 * @param strSource 原始字符串
	 * @param strFrom 开始字符串
	 * @param strTo 结束字符串
	 * @return 字符串
	 */
	public static String find(String strSource, String strFrom, String strTo) {
		String strDest = "";
		int intFromIndex = strSource.indexOf(strFrom) + strFrom.length();
		int intToIndex = strSource.indexOf(strTo);
		if (intFromIndex < intToIndex) {
			strDest = strSource.substring(intFromIndex, intToIndex);
		}
		return strDest;
	}

	/**
	 * 字符串拆分
	 * 
	 * @param sInputStr 字符串
	 * @param cDelimiter 拆分字符
	 * @return 字符串数组
	 */
	public static String[] split(String sInputStr, char cDelimiter) {
		int iStrLen = sInputStr.length();
		int iTokCount = 0;

		if (0 == iStrLen)
			return null;

		for (int p = 0; p < iStrLen; p++)
			if (sInputStr.charAt(p) == cDelimiter)
				iTokCount++;

		String Tokens[] = new String[iTokCount + 1];

		int iToken = 0;
		int iLast = 0;
		for (int iNext = 0; iNext < iStrLen; iNext++) {
			if (sInputStr.charAt(iNext) == cDelimiter) {
				if (iLast == iNext)
					Tokens[iToken] = "";
				else
					Tokens[iToken] = sInputStr.substring(iLast, iNext);
				iLast = iNext + 1;
				iToken++;
			} // fi (sInputStr[iNext]==cDelimiter)
		} // next

		if (iLast >= iStrLen)
			Tokens[iToken] = "";
		else
			Tokens[iToken] = sInputStr.substring(iLast, iStrLen);

		return Tokens;
	}

	/**
	 * 将字符串（包括汉字）分割成固定长度的字符串数组
	 * 
	 * @param strParamTarget 字符串
	 * @param nParamLen 固定长度
	 * @return 字符串数组
	 */
	public static String[] splitLength(String strParamTarget, int nParamLen) {
		Vector<String> vctWork = new Vector<String>();
		int nCharLen;

		int nLen = 0;

		int i;
		StringBuffer sbWork = new StringBuffer("");
		char cWork;

		if (strParamTarget == null) {

		} else {

			for (i = 0; i < strParamTarget.length(); i++) {

				cWork = strParamTarget.charAt(i);

				if (String.valueOf(cWork).getBytes().length > 1) {
					nCharLen = 2;
				} else {
					nCharLen = 1;
				}

				if ((nLen + nCharLen) > nParamLen) {

					vctWork.addElement(sbWork.toString());

					sbWork = new StringBuffer("");
					nLen = 0;
				}

				nLen += nCharLen;

				sbWork.append(cWork);
			}
			vctWork.addElement(sbWork.toString());
		}

		return (String[]) vctWork.toArray(new String[0]);
	}

	/**
	 * 手机号验证
	 *
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	/**
	 * 对GBK字符串进行转码成UTF-8
	 * 
	 * @param str 待解码字符串
	 * @return 字符串
	 * @throws Exception
	 */
	public static String strGBKtoUtf8(String str) throws Exception {

		String toStr = null;

		if (str != null) {
			try {
				toStr = new String(str.getBytes("gbk"), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

		return toStr;
	}

	/**
	 * 获取字符串中非GB2312字符
	 * 
	 * @param str 需要处理的字符串
	 * @return 字符串
	 */
	public static String getNotGB2312(String str) {
		str = nvl(str);
		char[] chars = str.toCharArray();
		String GB2312 = "";
		for (int i = 0; i < chars.length; i++) {
			try {
				byte[] bytes = ("" + chars[i]).getBytes("GB2312");
				if (bytes.length == 2) {
					int[] ints = new int[2];
					ints[0] = bytes[0] & 0xff;
					ints[1] = bytes[1] & 0xff;
					if (!(ints[0] >= 0xb0 && ints[0] <= 0xf7 && ints[1] >= 0xa1 && ints[1] <= 0xfe)) {
						GB2312 += chars[i];
					}
				} else {
					GB2312 += chars[i];
				}
			} catch (Exception e) {
				GB2312 += chars[i];
				System.out.println("ERR=====" + str);
				e.printStackTrace();
			}
		}
		return GB2312;
	}

	/**
	 * 取字符串的后几位，位数小于字符串，返回本身
	 * 
	 * @param string
	 * @param num 获取的位数
	 * @return
	 */
	public static String subString(String string, int num) {
		String returnValue = "";
		if (string != null) {
			int length = string.length();

			if (num > length) {
				returnValue = string;
			} else {
				returnValue = string.substring(length - num);
			}
		}
		return returnValue;
	}

	/**
	 * 替代oldStr中head之前，end之后的字符串
	 * 
	 * @param oldStr
	 * @param str
	 * @param head
	 * @param end
	 * @return String
	 * 
	 */
	public static String strReplace(String oldStr, String str, int head, int end) {
		StringBuffer result = new StringBuffer();
		if (oldStr == null || oldStr == "") {
			return result.toString();
		} else if (head > end || end > oldStr.length() || head < 0) {
			return oldStr;
		}

		for (int i = 0; i < head; i++) {
			result.append(str);
		}

		result.append(oldStr.substring(head, end));

		for (int i = end; i < oldStr.length(); i++) {
			result.append(str);
		}
		return result.toString();
	}

	/**
	 * 截取字符串，输入字符串长度大于要截取的长度，则显示“…”
	 * 
	 * @param input
	 * @param lettersNum 英文个数 ，一个中文占两个英文
	 * @return
	 */
	public static String subString2(String input, int lettersNum) {

		if (input == null || input.trim() == "") {
			return "";
		}
		String tmpStr = input.trim();

		if (tmpStr.length() * 2 <= lettersNum) {
			return tmpStr;
		}

		int num = 0;
		String temp = "";
		for (int i = 0; i < tmpStr.length() && num < lettersNum; i++) {
			if (tmpStr.substring(i, i + 1).getBytes().length > 1) {
				num += 2;
				temp = tmpStr.substring(0, i + 1);
			} else {
				num += 1;
				temp = tmpStr.substring(0, i + 1);
			}
		}

		if (temp.length() == tmpStr.length()) {
			return temp;
		} else {
			while (num > lettersNum - 2) {
				int i = temp.length();

				if (temp.substring(i - 1, i).getBytes().length > 1) {
					num = num - 2;
				} else {
					num = num - 1;
				}
				temp = temp.substring(0, i - 1);
			}
			temp += "…";
		}

		return temp;

	}
	
	/**
	 * 截取字符串，输入字符串长度大于要截取的长度，则显示“…”
	 * 
	 * @param input 输入字符串
	 * @param num 截取的长度
	 * @return String
	 */
	public static String subString3(String string, int num) {
		String returnValue = "";
		if (string != null) {
			int length = string.length();

			if (num >= length) {
				returnValue = string;
			} else {
				returnValue = string.substring(0, num) + "…";
			}
		}
		return returnValue;
	}

	/**
	 * 按字节截取字符串
	 * 
	 * @param sourceStr
	 * @param byteLen
	 * @return
	 */
	public static String cutStringByByte(String sourceStr, int byteLen) {
		if (sourceStr == null)
			return "";
		String targetStr = sourceStr;
		byte[] sourceByte = sourceStr.getBytes();
		if (sourceByte.length > byteLen) {
			targetStr = new String(sourceByte, 0, byteLen);
		}
		return targetStr;
	}

	/**
	 * 把ISO-8859-1码转换成UTF-8
	 * 
	 * @param sISO
	 * @param sDBEncoding
	 * @return 字符串
	 */
	public static String ISOConvertUTF(String sISO, String sDBEncoding) {

		String sUTF;
		try {
			if (sISO == null || sISO.equals("")) {
				return "";
			} else {
				sISO = sISO.trim();
				sUTF = new String(sISO.getBytes(sDBEncoding), "GBK");
				return sUTF;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * 把UTF-8码转换成ISO-8859-1
	 * 
	 * @param sUTF
	 * @param sDBEncoding
	 * @return 字符串
	 */
	public static String UTFConvertISO(String sUTF, String sDBEncoding) {

		String sISO;
		try {
			if (sUTF == null || sUTF.equals("")) {
				return "";
			} else {
				sUTF = sUTF.trim();
				sISO = new String(sUTF.getBytes("GBK"), sDBEncoding);
				return sISO;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * 把字符转换为Unicode(&#x????)编码
	 * 
	 * @param 转换前字符
	 * @return 转换后字符
	 */
	public static String toUnicodeX(String str) {
		char[] arChar = str.toCharArray();
		int iValue = 0;
		String uStr = "";
		for (int i = 0; i < arChar.length; i++) {
			iValue = (int) str.charAt(i);
			if (iValue <= 256) {
				uStr += "&#x00" + Integer.toHexString(iValue) + ";";
			} else {
				uStr += "&#x" + Integer.toHexString(iValue) + ";";
			}
		}
		return uStr;
	}

	/**
	 * 把字符转换为Unicode(\\u????)编码
	 * 
	 * @param 转换前字符
	 * @return 转换后字符
	 */
	public static String toUnicodeU(String str) {
		char[] arChar = str.toCharArray();
		int iValue = 0;
		String uStr = "";
		for (int i = 0; i < arChar.length; i++) {
			iValue = (int) str.charAt(i);
			if (iValue <= 256) {
				uStr += "\\u00" + Integer.toHexString(iValue);
			} else {
				uStr += "\\u" + Integer.toHexString(iValue);
			}
		}
		return uStr;
	}

	/**
	 * 判断字符串是否为null或者trim后长度小于1
	 * 
	 * @param arg 要被判断的字符串
	 * @return true 为null或者trim后长度小于1
	 */
	public static boolean isEmpty(String arg) {
		if (arg == null || arg.trim().length() < 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param num
	 * @return
	 */
	public static String subZeroAndDot(Object num) {
		String str = nvl(num);
		if (str.indexOf(".") > 0) {
			// 去掉多余的0
			str = str.replaceAll("0+?$", "");
			// 如最后一位是.则去掉
			str = str.replaceAll("[.]$", "");
		}
		return str;
	}

	/**
	 * 数字格式化
	 * 
	 * @param num
	 * @return
	 */
	public static String formatNum(Object num) {
		try {
			BigDecimal bNum = new BigDecimal(nvlnum(num)).setScale(2, BigDecimal.ROUND_HALF_UP);
			DecimalFormat df = new DecimalFormat("#,##0.00");
			return df.format(bNum);

		} catch (Exception e) {
			return String.valueOf(num);
		}
	}
	
	public static String formatNum3(Object num) {
		try {
			BigDecimal bNum = new BigDecimal(nvlnum(num)).setScale(3, BigDecimal.ROUND_HALF_UP);
			DecimalFormat df = new DecimalFormat("#,##0.000");
			return df.format(bNum);
			
		} catch (Exception e) {
			return String.valueOf(num);
		}
	}
	public static String formatNum4(Object num) {
		try {
			BigDecimal bNum = new BigDecimal(nvlnum(num)).setScale(1, BigDecimal.ROUND_HALF_UP);
			DecimalFormat df = new DecimalFormat("#,##0.0");
			return df.format(bNum);

		} catch (Exception e) {
			return String.valueOf(num);
		}
	}
	
	/**
	 * 数字格式化
	 * 
	 * @param num
	 * @return
	 */
	public static String formatNum1(Object num) {
		try {
			BigDecimal bNum = new BigDecimal(nvlnum(num)).setScale(0, BigDecimal.ROUND_HALF_UP);
			DecimalFormat df = new DecimalFormat("#,##0");
			return df.format(bNum);
			
		} catch (Exception e) {
			return String.valueOf(num);
		}
	}

	/**
	 * 取得供暖年度
	 * 
	 * @param ymd
	 * @return
	 */
	public static String getAnnual(String ymd) {

		String yyyy = ymd.split("-")[0];
		String nextYyyy = String.valueOf(new BigDecimal(yyyy).add(BigDecimal.ONE));
		String lastYyyy = String.valueOf(new BigDecimal(yyyy).subtract(BigDecimal.ONE));
		// 当年五月一日
		String fromYyyyMMdd = yyyy + "-05-01";
		// 次年四月三十日
		String toMMdd = nextYyyy + "-04-30";

		String retAnnual = "";
		if (ymd.compareTo(fromYyyyMMdd) >= 0 && toMMdd.compareTo(ymd) >= 0) {
			retAnnual = yyyy + "-" + nextYyyy;

		} else {
			retAnnual = lastYyyy + "-" + yyyy;
		}
		return retAnnual;
	}

	/**
	 * 
	 * @Description: 解决参数中有中文乱码的问题，可以将参数在Action中进行重新编码
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容。（可选）
	 * @author weizs
	 * @date 2015-02-03 下午3:35:53
	 * @version V1.0
	 */
	public static String Encode2UTF(String parm) {
		if (parm == null)
			return "";
		try {
			parm = new String(parm.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return parm;
	}

	public static boolean isChinese(char a) {
		int v = (int) a;
		return (v >= 19968 && v <= 171941);
	}

	/**
	 * 判断字符串是否为汉字
	 * 
	 * @param s
	 * @return true:汉字；false:汉字以外
	 */
	public static boolean containsChinese(String s) {
		if (null == s || "".equals(s.trim()))
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (isChinese(s.charAt(i)))
				return true;
		}
		return false;
	}
	
	/**
	 * 字符串补空格（按字节）
	 * 
	 * @param inStr
	 * @param length
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String fillSpace(String inStr, int length) throws UnsupportedEncodingException {
		inStr = nvl(inStr);
		byte[] bytes = inStr.getBytes("GB2312");

		if (bytes.length > length) {
			// 原始字符不为null，也不是空字符串
			if (inStr != null && !"".equals(inStr)) {
				// 将原始字符串转换为GBK编码格式
				inStr = new String(bytes, "GB2312");
				// 要截取的字节数大于0，且小于原始字符串的字节数
				if (length > 0 && length < bytes.length) {
					StringBuffer buff = new StringBuffer();
					char c;
					for (int i = 0; i < length; i++) {
						// charAt(int index)也是按照字符来分解字符串的
						c = inStr.charAt(i);
						buff.append(c);
						if (String.valueOf(c).getBytes("GB2312").length > 1) {
							// 遇到中文汉字，截取字节总数减1
							--length;
						}
					}
					return buff.toString();
				}
			}

		} else {
			while (bytes.length < length) {
				inStr = inStr + " ";
				bytes = inStr.getBytes("GB2312");
			}
		}

		String retStr = new String(bytes, "GB2312");
		return retStr;
	}

	/**
	 * HTML字符串补空格
	 * 
	 * @param inStr
	 * @param length
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String HtmlFillSpace(String inStr, int length) throws UnsupportedEncodingException {
		inStr = nvl(inStr);
		if (inStr.length() > length) {
			// 原始字符不为null，也不是空字符串
			if (inStr != null && !"".equals(inStr)) {
				// 要截取的字节数大于0，且小于原始字符串的字节数
				if (length > 0) {
					inStr = inStr.substring(0, length);
				}
			}
		} else {
			while (inStr.length() < length) {
				inStr = inStr + "&";
			}
		}
		inStr = inStr.replaceAll("&", "&nbsp;&nbsp;");
		return inStr;
	}
	
	/**
	 * 户主姓名（包含身份证的处理）
	 * 
	 * @param sUserName String
	 * @return String
	 */
	public static String formatCardNumber(String sUserName) {
		sUserName = nvl(sUserName);
		if (sUserName.contains("(")) {
			// 取得身份证号（含括号）
			String cardNumber = sUserName.substring(sUserName.indexOf("("), sUserName.indexOf(")") + 1);
			// 身份证号（含括号）长度
			int cardNumberLen = cardNumber.length();
			// 身份证号（含括号）长度异常时，直接返回
			if(cardNumberLen < 9) return sUserName;
			// 身份证号*号处理
			String star = "";
			for (int i = 0; i < cardNumberLen - 9; i++) {
				star = star + "*";
			}
			// 身份证号除前3位、后4外 其余*号处理
			cardNumber = cardNumber.substring(0, 4) + star + cardNumber.substring(cardNumberLen - 5, cardNumberLen);
			sUserName = sUserName.substring(0, sUserName.indexOf("(")) + cardNumber;
		} else if ("".equals(sUserName)){
			sUserName = "--";
		}
		return sUserName;
	}

	/**
	 * 手机号显示处理（*）
	 * 
	 * @param sTelNum String
	 * @return String
	 */
	public static String formatTelNum(String sTelNum) {
		sTelNum = nvl(sTelNum);
		if (!"".equals(sTelNum) && sTelNum.length() > 10) {
			sTelNum = sTelNum.substring(0, 3) + "****" + sTelNum.substring(7, 11);
		} else {
			sTelNum = "--";
		}
		return sTelNum;
	}
	
	/**
	 * 用热地址显示处理（显示后10个字，前边显示*）
	 * 
	 * @param useHeatAddress String
	 * @return String
	 */
	public static String formatUseHeatAddress(String useHeatAddress) {
		useHeatAddress = nvl(useHeatAddress);
		if ("".equals(useHeatAddress)) {
			useHeatAddress = "--";
			
		} else if (!"".equals(useHeatAddress) && useHeatAddress.length() > 10) {
			useHeatAddress = "***" + useHeatAddress.substring(useHeatAddress.length() - 10, useHeatAddress.length());
		}
		return useHeatAddress;
	}

	/**
	 * 按字节截取，从startLen开始，截length位
	 * 
	 * @param inStr
	 * @param startLen
	 * @param length
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String substr(String inStr, int startLen, int length) {
		try {
			byte[] bytes = inStr.getBytes("GB2312");
			if (bytes.length < startLen + length) {
				return "";
			}
			
			byte[] bb = new byte[length];
			System.arraycopy(bytes, startLen, bb, 0, length);
			String retStrs = new String(bb, "GB2312");
			
			return retStrs.trim();
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getIpAddr(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		if(ip.indexOf(":")>0){
			ip="127.0.0.1";
		}
		return ip;
	}

	/**
	 * 取得客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserAgent(HttpServletRequest request) {
		String userua = request.getHeader("user-agent");
		return userua;
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1 第一点经度
	 * @param lat1 第一点纬度
	 * @param long2 第二点经度
	 * @param lat2 第二点纬度
	 * 
	 * @return 返回距离 单位：米
	 */
	public static double Distance(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	
	/**
	 * 保留2位小数 四舍五入
	 * 
	 * @param double
	 * 
	 * @return 0.00
	 */
	public static double changeDouble(Double dou){
        dou = Double.parseDouble(String.format("%.2f", dou));
        return dou;
    }

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(StringUtil.subZeroAndDot(StringUtil.formatNum3("12")));
		
		double dostance = StringUtil.Distance(116.457107, 39.954171, 116.468839, 39.955097);
		
		System.err.println(dostance);
	}

	//判断字段是否包含英文及数字以外的字符
	public static boolean isNumString(String str){
		for(int i=0; i<str.length(); i++) {
			char c = str.charAt(i);
			if(c >= 'A' && c <= 'Z') {

			}
			else if( c >= 'a' && c <='z') {

			}
			else if(c=='_')
			{

			}
			else
			{
				try
				{
					Integer.parseInt(""+c);
				}catch(Exception d)
				{
					return true;
				}
			}
		}
		return false;
	}

	//判断字符串第一位是否为数字
	public static boolean indexNum(String str){
		if(str.length()==0)
		{
			return false;
		}
		char c = str.charAt(0);
		try
		{
			Integer.parseInt(""+c);
		}catch(Exception d)
		{
			return true;
		}
		return false;
	}
//将集合内的|%替换成,
	public static  List<Map> replace(List<Map> list) {
		for (int i = 0; i < list.size(); i++) {
			Map map = new HashMap();
			Iterator<Map.Entry<String, String>> it = list.get(i).entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				String key=entry.getKey();
				String value=StringUtil.nvl(entry.getValue()).replace("|%", ",");
				map.put(key, value);
			}
			list.remove(i);
			list.add(i,map);
		}
		return list;
	}


}