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
 * Debit Operation.
 * Decrease Balance for given amount.
 * Amount must have non-negative value. Use {@link com.loyaltyplant.test.domain.operation.CreditOperation}
 * if you want to increase balance.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
@Entity
@DiscriminatorValue("debit")
public class DebitOperation extends AbstractOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DebitOperation.class);

    @Column(name = "amount", precision = 16, scale = 8, nullable = false, updatable = false)
    private BigDecimal amount;

    public DebitOperation(@Nonnegative BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Can't debit negative amount");
        }

        this.amount = amount;
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
        if (!(obj instanceof DebitOperation)) {
            return false;
        } else {
            return isEquals((DebitOperation) obj);
        }
    }

    public boolean isEquals(@Nonnull DebitOperation other) {
        // EqualsBuilder uses 'equals' method to compare BigDecimals, which is not always correct.
        return other.getAmount().compareTo(getAmount()) == 0;
    }

    @Override
    public int hashCode() {
        return getAmount().hashCode();
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

    /**
     * Check if Balance value is greater then or equals Debit amount.
     *
     * @param balance Balance to check against
     * @return true if Balance has sufficient value
     */
    @Override
    public boolean isApplicableTo(@Nonnull Balance balance) {
        return balance.getAmount().compareTo(getAmount()) >= 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
