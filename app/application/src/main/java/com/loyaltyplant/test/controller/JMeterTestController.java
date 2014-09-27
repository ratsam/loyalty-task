package com.loyaltyplant.test.controller;

import com.loyaltyplant.test.domain.Balance;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Controller
@RequestMapping("/jmeter/test")
public class JMeterTestController {

    @PersistenceContext
    private EntityManager em;

    @RequestMapping("/setup")
    @Transactional(propagation = Propagation.REQUIRED)
    @ResponseBody
    public Integer setUpDatabase() {
        // Clear database
        em.createQuery("delete from Transaction").executeUpdate();
        em.createQuery("delete from Order").executeUpdate();
        em.createQuery("delete from AbstractOperation").executeUpdate();
        em.createQuery("delete from Balance").executeUpdate();

        // Create 1000 new Balances
        Integer startId = em.merge(new Balance(null, new BigDecimal("10.0"))).getId();
        for (int i = 0; i< 999; i++) {
            em.merge(new Balance(i, new BigDecimal("10.0")));
        }

        return startId;
    }

    @RequestMapping("/validate")
    @ResponseBody
    public void validate() {
        @Nullable
        BigDecimal credit = (BigDecimal) em.createQuery("select sum(o.amount) from CreditOperation o").getSingleResult();
        @Nullable
        BigDecimal debit = (BigDecimal) em.createQuery("select sum(o.amount) from DebitOperation o").getSingleResult();

        // TODO: validate credit equals debit
        // TODO: validate sum of balances is equals to 1000 * 10 (initial amount)
    }
}
