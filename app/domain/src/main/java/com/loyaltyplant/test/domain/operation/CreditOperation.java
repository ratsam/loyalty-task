package com.loyaltyplant.test.domain.operation;

import com.loyaltyplant.test.domain.Balance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
@Entity
@DiscriminatorValue("credit")
public class CreditOperation extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(CreditOperation.class);

    @Column(name = "amount", precision = 16, scale = 8, nullable = false, updatable = false)
    private BigDecimal amount;

    public CreditOperation() {

    }

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
