package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.domain.Order;
import com.loyaltyplant.test.domain.Transaction;
import com.loyaltyplant.test.repository.TransactionRepository;
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

    private TransactionRepository transactionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Transaction performTransaction(Collection<Order> orders, String description) {
        final Transaction transaction = new Transaction();
        transaction.setTime(DateTime.now());
        transaction.setOrders(new ArrayList<Order>(orders));
        transaction.setDescription(description);
        Transaction persistedTransaction =  transactionRepository.save(transaction);

        LOG.info("Persisted new Transaction: {}", persistedTransaction);

        return persistedTransaction;
    }

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
