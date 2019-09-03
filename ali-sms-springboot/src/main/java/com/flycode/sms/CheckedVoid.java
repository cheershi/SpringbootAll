package com.flycode.sms;

/**
 * The interface CheckedVoid.
 *
 * @author zhangpeng
 */
@FunctionalInterface
interface CheckedVoid {
    /**
     * Call.
     *
     * @throws Exception the exception
     */
    void call() throws Exception;
}
