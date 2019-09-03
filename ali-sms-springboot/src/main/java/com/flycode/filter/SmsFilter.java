package com.flycode.filter;

/**
 * @author FLY
 * @version 1.0.0
 * @date 2018-09-17
 * @blog http://uniontech.top
 */
public interface SmsFilter {
    /**
     * 初始化该过滤器
     */
    void init() throws Exception;

    /**
     * 判断短信是否可以发送.
     * @param smsEntity 将要发送的短信内容
     * @return 可以发送则返回true, 否则返回false
     */
    String filter(SmsEntity smsEntity);

    /**
     * 销毁该过滤器
     */
    void destroy();



}
