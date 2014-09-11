package com.loyaltyplant.test.domain.operation;

import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.domain.BalanceOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * Deposit Operation.
 * Perform account replenishment for given amount.
 * Amount must have non-negative value.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public class DepositOperation implements BalanceOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DepositOperation.class);

    private final BigDecimal amount;

    public DepositOperation(@Nonnegative BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("0")) < 0) {
            throw new IllegalArgumentException("Can't deposit negative amount");
        }

        this.amount = amount;
    }

    @Override
    public void apply(@Nonnull Balance balance) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Applying deposit of {} for Balance #{}", getAmount(), balance.getId());
        }
        doApply(balance);
    }

    private void doApply(Balance balance) {
        final BigDecimal newAmount = balance.getAmount().add(getAmount());
        balance.setAmount(newAmount);
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
