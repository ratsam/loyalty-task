package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.repository.BalanceRepository;
import com.loyaltyplant.test.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Service("balanceService")
public class BalanceServiceImpl implements BalanceService {

    private BalanceRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T extends Balance> T save(T balance) {
        return repository.save(balance);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T extends Balance> Iterable<T> save(Iterable<T> entities) {
        return repository.save(entities);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Balance findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Balance> findAll(Pageable pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Autowired
    public void setRepository(BalanceRepository repository) {
        this.repository = repository;
    }
}
