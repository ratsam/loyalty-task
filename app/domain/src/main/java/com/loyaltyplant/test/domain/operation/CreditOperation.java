package com.loyaltyplant.test.domain.operation;

import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.domain.BalanceOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * Credit Operation.
 * Perform balance replenishment for given amount.
 * Amount must have non-negative value. Use {@link com.loyaltyplant.test.domain.operation.DebitOperation}
 * if you want to decrease balance.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public class CreditOperation implements BalanceOperation {

    private static final Logger LOG = LoggerFactory.getLogger(CreditOperation.class);

    private final BigDecimal amount;

    public CreditOperation(@Nonnegative BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Can't credit negative amount");
        }

        this.amount = amount;
    }

    @Override
    public void apply(@Nonnull Balance balance) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Applying credit of {} for Balance #{}", getAmount(), balance.getId());
        }
        doApply(balance);
    }

    private void doApply(Balance balance) {
        final BigDecimal newAmount = balance.getAmount().add(getAmount());
        balance.setAmount(newAmount);
    }

    /**
     * Credit operation can be performed to any Balance.
     *
     * @param balance Balance to check against
     * @return true
     */
    @Override
    public boolean isApplicableTo(@Nonnull Balance balance) {
        return true;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
