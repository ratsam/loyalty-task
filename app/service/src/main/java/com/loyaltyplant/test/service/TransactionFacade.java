package com.loyaltyplant.test.service;

import com.loyaltyplant.test.domain.BalanceOperation;

import java.math.BigDecimal;

/**
 * Facade implementing most frequent operations.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public interface TransactionFacade {

    /**
     * Transfer funds from one Balance to another.
     * Return nothing (probably we should return pair with new Balance values).
     * Can throw exceptions if transfer can't be performed (to be described).
     *
     * @param fromBalance Balance id to debit
     * @param toBalance Balance id to credit
     * @param amount amount to transfer
     */
    void transfer(Integer fromBalance, Integer toBalance, BigDecimal amount);

    /**
     * Credit Balance for given amount.
     *
     * @param balanceId Balance id to credit
     * @param amount amount to credit
     * @return new Balance value
     */
    BigDecimal credit(Integer balanceId, BigDecimal amount);

    /**
     * Debit Balance with given amount.
     *
     * @param balanceId Balance id to debit
     * @param amount amount to debit
     * @return new Balance value
     */
    BigDecimal debit(Integer balanceId, BigDecimal amount);

    /**
     * Apply special Operation for Balance with given id.
     *
     * @param balanceId Balance id to apply Operation for
     * @param operation Operation to apply
     * @return new Balance value
     */
    BigDecimal applyOperation(Integer balanceId, BalanceOperation operation);
}
