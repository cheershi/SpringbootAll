package com.flycode.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther FLY
 *
 * 防范恶意发送错误代码
 * 及对应含义
 */
public class ErrorCode {

	public static String SUCESS = "OK";
	public static String FREQUENCY_TOO_HIGH_NUMBER = "手机号发送频率过高:";
	public static String FREQUENCY_TOO_HIGH_IP = "IP地址发送频率过高:";
	public static String EXCEEDING_TIMES_LIMIT_NUMBER = "手机号超出次数限制:";
	public static String EXCEEDING_TIMES_LIMIT_IP = "IP地址超出次数限制:";
	public static String ILLEGAL_PARAMETER_NUMBER = "手机格式错误:";
	public static String ILLEGAL_PARAMETER_IP = "IP地址格式错误:";


	public static String NO_TEMPLATE_CONFIG = "没有该短信模板配置";
	public static String NO_TEMPLATE_CONFIG_PROJECT = "该项目下没有此模板配置";

}
