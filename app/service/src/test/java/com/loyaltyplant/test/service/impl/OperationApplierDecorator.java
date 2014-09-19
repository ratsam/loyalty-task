package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.domain.operation.AbstractOperation;
import com.loyaltyplant.test.service.OperationApplier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * {@link com.loyaltyplant.test.service.OperationApplier} decorator that enforces starting new DB transaction.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public class OperationApplierDecorator implements OperationApplier {

    private final OperationApplier target;

    public OperationApplierDecorator(OperationApplier target) {
        this.target = target;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public BigDecimal applyOperation(Integer balanceId, AbstractOperation operation) {
        return target.applyOperation(balanceId, operation);
    }
}
