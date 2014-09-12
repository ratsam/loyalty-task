package com.loyaltyplant.test.domain.operation;

import com.loyaltyplant.test.domain.Balance;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class CreditOperationTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionWithNegativeAmount() {
        new CreditOperation(new BigDecimal("-0.1"));
    }

    @Test
    public void testConstructionWithZeroAmount() {
        // I don't know why, but why not?
        new CreditOperation(new BigDecimal("0"));
    }

    @Test
    public void testConstructWithPositiveAmount() {
        new CreditOperation(new BigDecimal("1"));
    }

    @Test
    public void testApplyZeroDeposit() {
        final Balance balance = new Balance(1, new BigDecimal("0.1"));

        new CreditOperation(new BigDecimal("0")).apply(balance);

        assertEquals(new BigDecimal("0.1"), balance.getAmount());
    }

    @Test
    public void testApplyPositiveDeposit() {
        final Balance balance = new Balance(1, new BigDecimal("0"));

        new CreditOperation(new BigDecimal("42")).apply(balance);

        assertEquals(new BigDecimal("42"), balance.getAmount());
    }
}
