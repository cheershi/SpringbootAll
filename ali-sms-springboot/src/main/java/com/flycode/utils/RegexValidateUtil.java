package com.flycode.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidateUtil {

	/**
	 * ClassName: RegexValidateUtil
	 * @Description: 正则表达式校验工具类
	 * @author chisj chisj@foxmial
	 * @date 2016年11月16日
	 */


		public static void main(String[] args) {
			System.out.println(checkEmail("14_8@qw.df"));
			System.out.println(checkMobileNumber("15071392085"));
		}
		/**
		 * 邮箱校验
		 * @param email
		 * @return
		 */
		public static boolean checkEmail(String email){
			boolean flag = false;
			try {
				String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
				Pattern regex = Pattern.compile(check);
				Matcher matcher = regex.matcher(email);
				flag = matcher.matches();

			} catch(Exception e) {
				flag = false;
			}
			return flag;
		}

		/**
		 * 手机号校验
		 * @param mobileNumber
		 * @return
		 */
		public static boolean checkMobileNumber(String mobileNumber){
			boolean flag = false;
			try {
//				Pattern regex = Pattern.compile("^(((13[0-9])|(17[0-9])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
				Pattern regex = Pattern.compile( "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
				Matcher matcher = regex.matcher(mobileNumber);
				flag = matcher.matches();
			} catch(Exception e) {
				flag = false;
			}
			return flag;
		}
		/**
		 * IP校验
		 * @param
		 * @return
		 */
		public static boolean checkIPAddress(String ipaddr) {
			boolean flag = false;
			Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
			Matcher m = pattern.matcher(ipaddr);
			flag = m.matches();
			return flag;
		}
}