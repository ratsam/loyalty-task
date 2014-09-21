package com.loyaltyplant.test.service;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;

import java.math.BigDecimal;

/**
 * Annotation {@code @Retryable} is put here duw to Spring-retry bug.
 * Spring tries to obtain annotation information in runtime, before the method invocation, and eventually
 * looks for annotation at the interface level (place where method is defined).
 * See {@link org.springframework.retry.annotation.AnnotationAwareRetryOperationsInterceptor#getDelegate(java.lang.Object, java.lang.reflect.Method)}
 * for implementation details.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public interface BalanceTransferService {

    /**
     * Transfer funds from one Balance to another.
     * Return nothing (probably we should return pair with new Balance values).
     * Can throw exceptions if transfer can't be performed (to be described).
     *
     * @param fromBalance Balance id to debit
     * @param toBalance Balance id to credit
     * @param amount amount to transfer
     */
    @Retryable(include = OptimisticLockingFailureException.class, maxAttempts = 1000)
    void transfer(Integer fromBalance, Integer toBalance, BigDecimal amount);

    /**
     * Credit Balance for given amount.
     *
     * @param balanceId Balance id to credit
     * @param amount amount to credit
     */
    @Retryable(include = OptimisticLockingFailureException.class, maxAttempts = 1000)
    void credit(Integer balanceId, BigDecimal amount);

    /**
     * Debit Balance with given amount.
     *
     * @param balanceId Balance id to debit
     * @param amount amount to debit
     */
    @Retryable(include = OptimisticLockingFailureException.class, maxAttempts = 1000)
    void debit(Integer balanceId, BigDecimal amount);
}
