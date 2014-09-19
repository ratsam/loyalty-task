package com.loyaltyplant.test.service.impl;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.domain.Order;
import com.loyaltyplant.test.domain.operation.AbstractOperation;
import com.loyaltyplant.test.domain.operation.CreditOperation;
import com.loyaltyplant.test.repository.BalanceRepository;
import com.loyaltyplant.test.service.TransactionPerformService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TransactionPerformServiceImplTest.Config.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
public class TransactionPerformServiceImplTest {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransactionPerformService transactionPerformService;

    @Test(expected = IllegalArgumentException.class)
    public void testPerformEmptyTransaction() {
        transactionPerformService.performTransaction(Collections.<Order<? extends AbstractOperation>>emptyList(), null);
    }

    @Test
    @DatabaseSetup("balances-before.xml")
    @ExpectedDatabase(value = "balances-after-simple-transaction.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testPerformTransaction() {
        transactionPerformService.performTransaction(Collections.<Order<? extends AbstractOperation>>singleton(prepareSimpleOrder()), null);
    }

    private Order<CreditOperation> prepareSimpleOrder() {
        final Balance balance = balanceRepository.findById(1);

        final Order<CreditOperation> order = new Order<>();
        order.setBalance(balance);
        order.setOperation(new CreditOperation(new BigDecimal("10")));
        return order;
    }

    @EnableAutoConfiguration
    @EnableTransactionManagement
    @EntityScan("com.loyaltyplant.test.domain")
    @EnableJpaRepositories("com.loyaltyplant.test.repository")
    public static class Config {

        @Bean(autowire = Autowire.BY_TYPE)
        public TransactionPerformService transactionPerformService() {
            return new TransactionPerformServiceImpl();
        }
    }
}
