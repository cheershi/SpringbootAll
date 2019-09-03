package com.flycode.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Random;

/**
 * IP工具类
 * 
 */
public class IpUtil {
	
	static Random rd = new Random(System.currentTimeMillis());

	/**
	 * 获取登录用户IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		return ip;
	}
	
	public static long ip2long(String ip) {
		String[] items = ip.split("\\.");

		return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
	}
	
	public static String long2ip(long ipLong) {
		StringBuilder sb = new StringBuilder();
		sb.append((ipLong >> 24) & 0xFF).append(".");
		sb.append((ipLong >> 16) & 0xFF).append(".");
		sb.append((ipLong >> 8) & 0xFF).append(".");
		sb.append(ipLong & 0xFF);
		return sb.toString();
	}
	
	/**
	 * 模拟随机用户IP
	 */
	public static String getRandomIpAddr() {
		StringBuffer ip = new StringBuffer();

		ip.append(rd.nextInt(256));
		ip.append(".");
		ip.append(rd.nextInt(256));
		ip.append(".");
		ip.append(rd.nextInt(256));
		ip.append(".");
		ip.append(rd.nextInt(256));
		
		return ip.toString();
	}
	
	
	/**
	 * 判断当前服务器是否拥有IP地址
	 * @return boolean
	 */
	public static boolean checkIP(String ip) {
		try {
			Enumeration<NetworkInterface> enumNetwork = NetworkInterface.getNetworkInterfaces();
			while (enumNetwork.hasMoreElements()) {
				NetworkInterface netInterface = enumNetwork.nextElement();
				Enumeration<InetAddress> enumIp = netInterface.getInetAddresses();
				while (enumIp.hasMoreElements()) {
					InetAddress inetAdd = enumIp.nextElement();
					if (inetAdd.getHostAddress().equals(ip)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public static void main(String[] args) {
		
		int i = 0;
		while(i < 1000)
			System.out.println(getRandomIpAddr());
//		System.out.println(rd.nextInt(256));
	}
	
}
