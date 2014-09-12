package com.loyaltyplant.test.domain;

import java.io.Serializable;

/**
 * Order describes Operation that should be performed to Balance.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Balance balance;
    private BalanceOperation operation;

    public boolean isValid() {
        return operation.isApplicableTo(balance);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public BalanceOperation getOperation() {
        return operation;
    }

    public void setOperation(BalanceOperation operation) {
        this.operation = operation;
    }
}
