package com.loyaltyplant.test.domain;

import com.loyaltyplant.test.domain.operation.AbstractOperation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Order describes Operation that should be performed to Balance.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
@Entity
@Table(name = "loyalty_order")
public class Order<T extends AbstractOperation> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(optional = false)
    private Balance balance;

    @OneToOne(targetEntity = AbstractOperation.class, cascade = CascadeType.PERSIST)
    private T operation;

    public boolean isValid() {
        return operation.isApplicableTo(balance);
    }

    public void apply() {
        operation.apply(getBalance());
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

    public T getOperation() {
        return operation;
    }

    public void setOperation(T operation) {
        this.operation = operation;
    }
}
