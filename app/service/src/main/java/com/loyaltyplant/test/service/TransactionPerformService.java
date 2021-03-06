package com.loyaltyplant.test.service;

import com.loyaltyplant.test.domain.Order;
import com.loyaltyplant.test.domain.Transaction;
import com.loyaltyplant.test.domain.operation.AbstractOperation;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Low-level Transaction service.
 * Apply collection of Orders within single Transaction.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public interface TransactionPerformService {

    /**
     * Perform transaction.
     *
     * @param orders non-empty collection of orders
     * @param description optional description
     * @return performed transaction
     * @throws java.lang.IllegalArgumentException if {@code orders} is empty.
     * @throws com.loyaltyplant.test.service.OrderProcessingException if any Order is not valid and can't be processed
     */
    Transaction performTransaction(Collection<? extends Order<? extends AbstractOperation>> orders, @Nullable String description);
}
