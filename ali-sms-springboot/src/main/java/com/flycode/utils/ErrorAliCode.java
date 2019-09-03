package com.flycode.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther FLY
 * 阿里云返回错误代码含义映射表
 */
public class ErrorAliCode {

	private static Map<String,String> errorAliCodeMap=new HashMap<>();
	static {
		errorAliCodeMap.put("OK","OK");
		errorAliCodeMap.put("isp.RAM_PERMISSION_DENY","RAM权限DENY");
		errorAliCodeMap.put("isv.OUT_OF_SERVICE","业务停机");
		errorAliCodeMap.put("isv.PRODUCT_UN_SUBSCRIPT","未开通云通信产品的阿里云客户");
		errorAliCodeMap.put("isv.PRODUCT_UNSUBSCRIBE","产品未开通");
		errorAliCodeMap.put("isv.ACCOUNT_NOT_EXISTS","账户不存在");
		errorAliCodeMap.put("isv.ACCOUNT_ABNORMAL","账户异常");
		errorAliCodeMap.put("isv.SMS_TEMPLATE_ILLEGAL","短信模板不合法");
		errorAliCodeMap.put("isv.SMS_SIGNATURE_ILLEGAL","短信签名不合法");
		errorAliCodeMap.put("isv.INVALID_PARAMETERS","参数异常");
		errorAliCodeMap.put("isp.SYSTEM_ERROR","系统错误");
		errorAliCodeMap.put("isv.MOBILE_NUMBER_ILLEGAL","非法手机号");
		errorAliCodeMap.put("isv.MOBILE_COUNT_OVER_LIMIT","手机号码数量超过限制");
		errorAliCodeMap.put("isv.TEMPLATE_MISSING_PARAMETERS","模板缺少变量");
		errorAliCodeMap.put("isv.BUSINESS_LIMIT_CONTROL","业务限流");
		errorAliCodeMap.put("isv.INVALID_JSON_PARAM","JSON参数不合法，只接受字符串值");
		errorAliCodeMap.put("isv.BLACK_KEY_CONTROL_LIMIT","黑名单管控");
		errorAliCodeMap.put("isv.PARAM_LENGTH_LIMIT","参数超出长度限制");
		errorAliCodeMap.put("isv.PARAM_NOT_SUPPORT_URL","不支持URL");
		errorAliCodeMap.put("isv.AMOUNT_NOT_ENOUGH","账户余额不足");
	}

	public static String getCodeHint(String code){
		return errorAliCodeMap.get(code);
	}
}
