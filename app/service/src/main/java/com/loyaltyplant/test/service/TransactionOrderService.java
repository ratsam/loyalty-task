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

    Transaction performTransaction(Collection<Order> orders, @Nullable String description);
}
