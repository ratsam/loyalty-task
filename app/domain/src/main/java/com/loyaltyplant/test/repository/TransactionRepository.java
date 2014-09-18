package com.loyaltyplant.test.repository;

import com.loyaltyplant.test.domain.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@RepositoryDefinition(domainClass = Transaction.class, idClass = Integer.class)
public interface TransactionRepository {

    @Modifying
    <T extends Transaction> T save(T transaction);

    Transaction findById(Integer id);
}
