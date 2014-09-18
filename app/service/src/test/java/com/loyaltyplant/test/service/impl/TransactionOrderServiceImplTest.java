package com.loyaltyplant.test.service.impl;

import com.loyaltyplant.test.domain.Order;
import com.loyaltyplant.test.service.TransactionOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collections;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TransactionOrderServiceImplTest.Config.class)
public class TransactionOrderServiceImplTest {

    @Autowired
    private TransactionOrderService transactionOrderService;

    @Test(expected = IllegalArgumentException.class)
    public void testPerformEmptyTransaction() {
        transactionOrderService.performTransaction(Collections.<Order>emptyList(), null);
    }

    @EnableAutoConfiguration
    @EnableTransactionManagement
    @EntityScan("com.loyaltyplant.test.domain")
    @EnableJpaRepositories("com.loyaltyplant.test.repository")
    public static class Config {

        @Bean(autowire = Autowire.BY_TYPE)
        public TransactionOrderService transactionOrderService() {
            return new TransactionOrderServiceImpl();
        }
    }
}
