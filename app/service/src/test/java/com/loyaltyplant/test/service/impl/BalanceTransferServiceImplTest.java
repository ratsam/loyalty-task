package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.domain.operation.DebitOperation;
import com.loyaltyplant.test.service.BalanceTransferService;
import com.loyaltyplant.test.service.OperationApplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BalanceTransferServiceImplTest.Config.class)
public class BalanceTransferServiceImplTest {

    @Autowired
    private BalanceTransferService balanceTransferService;

    @Autowired
    private OperationApplier operationApplierMock;

    @Before
    public void setUp() {
        reset(operationApplierMock);
    }

    @Test
    public void testDebitSuccess() {
        balanceTransferService.debit(1, new BigDecimal("10"));

        verify(operationApplierMock).applyOperation(eq(Integer.valueOf(1)), eq(new DebitOperation(new BigDecimal("10.00"))));
        verifyNoMoreInteractions(operationApplierMock);
    }

    public static class Config {

        @Bean
        public OperationApplier operationApplierMock() {
            return mock(OperationApplier.class);
        }

        @Bean(autowire = Autowire.BY_TYPE)
        public BalanceTransferService balanceTransferService() {
            return new BalanceTransferServiceImpl();
        }
    }
}
