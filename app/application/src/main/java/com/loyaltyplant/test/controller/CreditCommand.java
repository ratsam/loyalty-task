package com.loyaltyplant.test.controller;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class CreditCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer to;

    @DecimalMin("0.00")
    private BigDecimal amount;

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
