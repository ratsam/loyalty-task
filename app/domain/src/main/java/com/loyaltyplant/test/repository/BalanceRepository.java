package com.loyaltyplant.test.repository;

import com.loyaltyplant.test.domain.Balance;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@RepositoryDefinition(domainClass = Balance.class, idClass = Integer.class)
public interface BalanceRepository {

    @Modifying
    <T extends Balance> T save(T balance);

    @Modifying
    <T extends Balance> Iterable<T> save(Iterable<T> entities);

    Balance findById(Integer id);
}
