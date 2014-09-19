package com.loyaltyplant.test.service;

import com.loyaltyplant.test.domain.operation.AbstractOperation;

import java.math.BigDecimal;

/**
 * Facade implementing most frequent operations.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public interface OperationApplier {

    /**
     * Apply special Operation for Balance with given id.
     *
     * @param balanceId Balance id to apply Operation for
     * @param operation Operation to apply
     * @return new Balance value
     */
    BigDecimal applyOperation(Integer balanceId, AbstractOperation operation);
}
