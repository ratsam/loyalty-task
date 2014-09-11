package com.loyaltyplant.test.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Object to store account balance.
 *
 * In real application you would extend this class with various important fields, such as "currency".
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public class Balance implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private BigDecimal amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
