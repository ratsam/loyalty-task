package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.domain.Order;
import com.loyaltyplant.test.domain.operation.AbstractOperation;
import com.loyaltyplant.test.repository.BalanceRepository;
import com.loyaltyplant.test.service.OperationApplier;
import com.loyaltyplant.test.service.OperationApplyingInfo;
import com.loyaltyplant.test.service.TransactionPerformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Service("operationApplier")
public class OperationApplierImpl implements OperationApplier {

    private BalanceRepository balanceRepository;
    private TransactionPerformService transactionPerformService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void applyOperation(Integer balanceId, AbstractOperation operation) {
        applyOperations(Collections.singleton(new OperationApplyingInfo(balanceId, operation)));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void applyOperations(Collection<OperationApplyingInfo> operationApplyingInfos) {
        final Collection<Order<AbstractOperation>> orders = new ArrayList<>(operationApplyingInfos.size());
        for (OperationApplyingInfo operationApplyingInfo : operationApplyingInfos) {
            final Balance balance = balanceRepository.findById(operationApplyingInfo.getBalanceId());
            final AbstractOperation operation = operationApplyingInfo.getOperation();
            final Order<AbstractOperation> order = new Order<>(balance, operation);
            orders.add(order);
        }
        transactionPerformService.performTransaction(orders, null);
    }

    @Autowired
    public void setBalanceRepository(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Autowired
    public void setTransactionPerformService(TransactionPerformService transactionPerformService) {
        this.transactionPerformService = transactionPerformService;
    }
}
