package com.loyaltyplant.test.controller;

import javax.annotation.Nonnegative;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class DebitCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer from;

    @Nonnegative
    private BigDecimal amount;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
