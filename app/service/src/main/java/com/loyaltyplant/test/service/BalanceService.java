package com.loyaltyplant.test.service;

import com.loyaltyplant.test.domain.Balance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;

/**
 * Transactional wrapper for {@link com.loyaltyplant.test.repository.BalanceRepository}.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
public interface BalanceService {

    <T extends Balance> T save(T balance);

    <T extends Balance> Iterable<T> save(Iterable<T> entities);

    Balance findById(Integer id);

    Page<Balance> findAll(Pageable pageRequest);
}
