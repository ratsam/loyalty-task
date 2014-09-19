package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.domain.Order;
import com.loyaltyplant.test.domain.operation.AbstractOperation;
import com.loyaltyplant.test.repository.BalanceRepository;
import com.loyaltyplant.test.service.TransactionFacade;
import com.loyaltyplant.test.service.TransactionOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Service("transactionFacade")
public class TransactionFacadeImpl implements TransactionFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionFacadeImpl.class);

    private BalanceRepository balanceRepository;
    private TransactionOrderService transactionOrderService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transfer(Integer fromBalance, Integer toBalance, BigDecimal amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BigDecimal credit(Integer balanceId, BigDecimal amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BigDecimal debit(Integer balanceId, BigDecimal amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BigDecimal applyOperation(Integer balanceId, AbstractOperation operation) {
        final Balance balance = balanceRepository.findById(balanceId);
        final Order<AbstractOperation> order = new Order<>(balance, operation);
        transactionOrderService.performTransaction(Collections.<Order<? extends AbstractOperation>>singleton(order), null);
        LOG.info("Updated balance #{}, new amount={} @version={}", balance.getId(), balance.getAmount(), balance.getVersion());
        return balance.getAmount();
    }

    @Autowired
    public void setBalanceRepository(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Autowired
    public void setTransactionOrderService(TransactionOrderService transactionOrderService) {
        this.transactionOrderService = transactionOrderService;
    }
}
