package com.loyaltyplant.test.service;

import com.loyaltyplant.test.domain.Order;
import com.loyaltyplant.test.domain.Transaction;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public interface TransactionOrderService {

    /**
     * Perform transaction.
     *
     * @param orders non-empty collection of orders
     * @param description optional description
     * @return performed transaction
     * @throws java.lang.IllegalArgumentException if {@code orders} is empty.
     */
    Transaction performTransaction(Collection<Order> orders, @Nullable String description);
}
