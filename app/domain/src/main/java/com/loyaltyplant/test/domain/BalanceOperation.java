package com.loyaltyplant.test.domain;

import javax.annotation.Nonnull;

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
    void apply(@Nonnull Balance balance);

    /**
     * Check if Operation can be performed on the given Balance.
     *
     * @param balance Balance to check against
     * @return true if operation can be performed otherwise false
     */
    boolean isApplicableTo(@Nonnull Balance balance);
}
