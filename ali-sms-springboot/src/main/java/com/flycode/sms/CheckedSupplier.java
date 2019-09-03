package com.flycode.sms;

/**
 * The interface CheckedSupplier.
 *
 * @param <T> the type parameter
 *
 * @author zhangpeng
 */
@FunctionalInterface
interface CheckedSupplier<T> {
    /**
     * Get t.
     *
     * @return the t
     *
     * @throws Exception the exception
     */
    T get() throws Exception;
}
