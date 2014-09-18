package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.domain.Order;
import com.loyaltyplant.test.domain.Transaction;
import com.loyaltyplant.test.domain.operation.AbstractOperation;
import com.loyaltyplant.test.repository.BalanceRepository;
import com.loyaltyplant.test.repository.TransactionRepository;
import com.loyaltyplant.test.service.OrderProcessingException;
import com.loyaltyplant.test.service.TransactionOrderService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Service("transactionOrderService")
public class TransactionOrderServiceImpl implements TransactionOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionOrderServiceImpl.class);

    private BalanceRepository balanceRepository;
    private TransactionRepository transactionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Transaction performTransaction(Collection<Order<? extends AbstractOperation>> orders, String description) {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("Can't perform transaction with no orders");
        }
        return doPerformTransaction(orders, description);
    }

    private Transaction doPerformTransaction(Collection<Order<? extends AbstractOperation>> orders, String description) {
        final Transaction transaction = new Transaction();
        transaction.setTime(DateTime.now());
        applyOrders(orders);
        transaction.setOrders(new ArrayList<>(orders));
        transaction.setDescription(description);

        Transaction persistedTransaction =  transactionRepository.saveAndFlush(transaction);

        LOG.info("Persisted new Transaction: {}", persistedTransaction);

        return persistedTransaction;
    }

    private void applyOrders(Collection<Order<? extends AbstractOperation>> orders) {
        final Collection<Balance> updatedBalances = new ArrayList<>(orders.size());

        for (Order order : orders) {
            if (!order.isValid()) {
                throw new OrderProcessingException();
            }
            order.apply();
            updatedBalances.add(order.getBalance());
        }

        balanceRepository.save(updatedBalances);
    }

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    public void setBalanceRepository(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }
}
