package com.loyaltyplant.test.controller;

import javax.annotation.Nonnegative;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class TransferCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer from;
    private Integer to;

    @Nonnegative
    private BigDecimal amount;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
