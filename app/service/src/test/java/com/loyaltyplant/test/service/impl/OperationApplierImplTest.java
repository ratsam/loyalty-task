package com.loyaltyplant.test.service.impl;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.domain.operation.DebitOperation;
import com.loyaltyplant.test.service.OperationApplier;
import com.loyaltyplant.test.service.TransactionPerformService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OperationApplierImplTest.Config.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
public class OperationApplierImplTest {

    @Autowired
    private OperationApplier operationApplier;

    @Test(expected = ObjectOptimisticLockingFailureException.class, timeout = 3000L)
    @DatabaseSetup("balances-before.xml")
    public void testOptimisticLockingFailure() {
        final CountDownLatch latch = new CountDownLatch(2);

        final ForkJoinPool pool = new ForkJoinPool(2);
        final ForkJoinTask<?> task = pool.submit(task(latch));
        pool.submit(task(latch)).join();
        task.join();
    }

    private Runnable task(final CountDownLatch latch) {
        return new Runnable() {
            @Override
            public void run() {
                operationApplier.applyOperation(2, new WaitingDebitOperation(new BigDecimal("7.0"), latch));
            }
        };
    }

    @Entity
    @DiscriminatorValue("waiting_debit")
    public static class WaitingDebitOperation extends DebitOperation {

        @Transient
        private CountDownLatch latch;

        public WaitingDebitOperation(@Nonnegative BigDecimal amount, CountDownLatch latch) {
            super(amount);
            this.latch = latch;
        }

        @Override
        public void apply(@Nonnull Balance balance) {
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            super.apply(balance);
        }
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableTransactionManagement
    @EntityScan({"com.loyaltyplant.test.domain", "com.loyaltyplant.test.service.impl"})
    @EnableJpaRepositories("com.loyaltyplant.test.repository")
    public static class Config {

        @Bean(autowire = Autowire.BY_TYPE)
        public TransactionPerformService transactionPerformService() {
            return new TransactionPerformServiceImpl();
        }

        @Bean(autowire = Autowire.BY_TYPE)
        public OperationApplier operationApplierImpl() {
            return new OperationApplierImpl();
        }

        @Bean
        @Primary
        public OperationApplier operationApplier() {
            return new OperationApplierDecorator(operationApplierImpl());
        }
    }
}
