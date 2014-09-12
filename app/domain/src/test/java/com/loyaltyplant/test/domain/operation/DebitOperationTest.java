package com.loyaltyplant.test.domain.operation;

import com.loyaltyplant.test.domain.Balance;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class DebitOperationTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionWithNegativeAmount() {
        new DebitOperation(new BigDecimal("-0.1"));
    }

    @Test
    public void testConstructionWithZeroAmount() {
        // I don't know why, but why not?
        new DebitOperation(new BigDecimal("0"));
    }

    @Test
    public void testConstructWithPositiveAmount() {
        new DebitOperation(new BigDecimal("1"));
    }

    @Test
    public void testApplyZeroDebit() {
        final Balance balance = new Balance(1, new BigDecimal("0.1"));

        new DebitOperation(new BigDecimal("0")).apply(balance);

        assertEquals(new BigDecimal("0.1"), balance.getAmount());
    }

    @Test
    public void testApplyPositiveDebit() {
        final Balance balance = new Balance(1, new BigDecimal("42"));

        new DebitOperation(new BigDecimal("21.5")).apply(balance);

        assertEquals(new BigDecimal("20.5"), balance.getAmount());
    }
}
