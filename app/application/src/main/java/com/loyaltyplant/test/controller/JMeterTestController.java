package com.loyaltyplant.test.controller;

import com.loyaltyplant.test.domain.Balance;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            em.persist(new Balance(null, new BigDecimal("10.0")));
        }

        em.createQuery("update Balance set id = id - :diff")
                .setParameter("diff", startId - 1)
                .executeUpdate();

        return startId;
    }

    @RequestMapping("/validate")
    @ResponseBody
    public String validate() {
        BigDecimal credit = (BigDecimal) em.createQuery("select sum(o.amount) from CreditOperation o").getSingleResult();
        BigDecimal debit = (BigDecimal) em.createQuery("select sum(o.amount) from DebitOperation o").getSingleResult();
        if (credit.compareTo(debit) != 0) {
            return "Failed: consistency error: sum(credit) != sum(debit)";
        }

        BigDecimal sumBalances = (BigDecimal) em.createQuery("select sum(b.amount) from Balance b").getSingleResult();
        if (sumBalances.compareTo(new BigDecimal("10000")) != 0) {
            return "Failed: consistency error: sum(balance.amount) != initial amount";
        }
        return "ok";
    }
}
