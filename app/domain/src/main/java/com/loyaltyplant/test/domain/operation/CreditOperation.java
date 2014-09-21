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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        // Hibernate doesn't allow us to check classes for equality.
        if (!(obj instanceof CreditOperation)) {
            return false;
        } else {
            return isEquals((CreditOperation) obj);
        }
    }

    public boolean isEquals(@Nonnull CreditOperation other) {
        // EqualsBuilder uses 'equals' method to compare BigDecimals, which is not always correct.
        return other.getAmount().compareTo(getAmount()) == 0;
    }

    @Override
    public int hashCode() {
        return getAmount().hashCode();
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
