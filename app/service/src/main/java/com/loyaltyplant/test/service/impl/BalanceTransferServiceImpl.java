package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.service.BalanceTransferService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class BalanceTransferServiceImpl implements BalanceTransferService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transfer(Integer fromBalance, Integer toBalance, BigDecimal amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BigDecimal credit(Integer balanceId, BigDecimal amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BigDecimal debit(Integer balanceId, BigDecimal amount) {
        throw new UnsupportedOperationException();
    }
}
