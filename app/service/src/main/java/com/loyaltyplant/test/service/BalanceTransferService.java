package com.loyaltyplant.test.service;

import java.math.BigDecimal;

/**
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
    void transfer(Integer fromBalance, Integer toBalance, BigDecimal amount);

    /**
     * Credit Balance for given amount.
     *
     * @param balanceId Balance id to credit
     * @param amount amount to credit
     */
    void credit(Integer balanceId, BigDecimal amount);

    /**
     * Debit Balance with given amount.
     *
     * @param balanceId Balance id to debit
     * @param amount amount to debit
     */
    void debit(Integer balanceId, BigDecimal amount);
}
