package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.domain.operation.CreditOperation;
import com.loyaltyplant.test.domain.operation.DebitOperation;
import com.loyaltyplant.test.service.BalanceTransferService;
import com.loyaltyplant.test.service.OperationApplier;
import com.loyaltyplant.test.service.OperationApplyingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Balance transfer service.
 * Handles Balance value modification in a safe manner, automatically retrying if
 * {@link org.springframework.dao.OptimisticLockingFailureException} occurs.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
@Service("balanceTransferService")
public class BalanceTransferServiceImpl implements BalanceTransferService {

    private OperationApplier operationApplier;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transfer(Integer fromBalance, Integer toBalance, BigDecimal amount) {
        operationApplier.applyOperations(Arrays.asList(
                new OperationApplyingInfo(fromBalance, new DebitOperation(amount)),
                new OperationApplyingInfo(toBalance, new CreditOperation(amount))
        ));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void credit(Integer balanceId, BigDecimal amount) {
        operationApplier.applyOperation(balanceId, new CreditOperation(amount));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void debit(Integer balanceId, BigDecimal amount) {
        operationApplier.applyOperation(balanceId, new DebitOperation(amount));
    }

    @Autowired
    public void setOperationApplier(OperationApplier operationApplier) {
        this.operationApplier = operationApplier;
    }
}
