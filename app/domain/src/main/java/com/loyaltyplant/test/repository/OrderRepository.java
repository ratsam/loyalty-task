package com.loyaltyplant.test.repository;

import com.loyaltyplant.test.domain.Order;
import com.loyaltyplant.test.domain.operation.AbstractOperation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@RepositoryDefinition(domainClass = Order.class, idClass = Integer.class)
public interface OrderRepository {

    @Modifying
    <O extends AbstractOperation, T extends Order<O>> T save(T order);

    Order<? extends AbstractOperation> findById(Integer id);
}
