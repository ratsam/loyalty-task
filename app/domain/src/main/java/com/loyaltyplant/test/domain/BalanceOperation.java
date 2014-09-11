package com.loyaltyplant.test.domain;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public interface BalanceOperation {

    /**
     * Update Balance <strong>in place</strong>.
     *
     * @param balance Balance to perform operation on
     */
    void apply(Balance balance);
}
