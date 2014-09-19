package com.loyaltyplant.test.service;

import com.loyaltyplant.test.domain.operation.AbstractOperation;

import java.io.Serializable;

/**
 * Container for {@link com.loyaltyplant.test.domain.operation.AbstractOperation} and
 * {@code balanceId} it should be applied to.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public class OperationApplyingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer balanceId;
    private final AbstractOperation operation;

    public OperationApplyingInfo(Integer balanceId, AbstractOperation operation) {
        this.balanceId = balanceId;
        this.operation = operation;
    }

    public Integer getBalanceId() {
        return balanceId;
    }

    public AbstractOperation getOperation() {
        return operation;
    }
}
