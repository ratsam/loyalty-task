package com.loyaltyplant.test.service;

import com.loyaltyplant.test.domain.operation.AbstractOperation;

import java.util.Collection;

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
     */
    void applyOperation(Integer balanceId, AbstractOperation operation);

    /**
     * Apply collection of Operations within single transaction.
     *
     * @param operationApplyingInfos collection with info about Operations to be applied
     */
    void applyOperations(Collection<OperationApplyingInfo> operationApplyingInfos);
}
