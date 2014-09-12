package com.loyaltyplant.test.domain.operation;

import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.domain.BalanceOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * Debit Operation.
 * Decrease Balance for given amount.
 * Amount must have non-negative value. Use {@link com.loyaltyplant.test.domain.operation.CreditOperation}
 * if you want to increase balance.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public class DebitOperation implements BalanceOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DebitOperation.class);

    private final BigDecimal amount;

    public DebitOperation(@Nonnegative BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Can't debit negative amount");
        }

        this.amount = amount;
    }

    @Override
    public void apply(@Nonnull Balance balance) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Applying debit of {} for Balance #{}", getAmount(), balance.getId());
        }
        doApply(balance);
    }

    private void doApply(Balance balance) {
        final BigDecimal newAmount = balance.getAmount().subtract(getAmount());
        balance.setAmount(newAmount);
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
