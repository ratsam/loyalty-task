package com.loyaltyplant.test.controller;

import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Controller
@RequestMapping("/api/balance")
public class BalanceApiController {

    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Page<Balance> getBalances() {
        return getBalances(new PageRequest(0, 20));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, params = "page")
    @ResponseBody
    public Page<Balance> getBalances(Pageable pageRequest) {
        return balanceService.findAll(pageRequest);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Balance createBalance(@RequestParam("initialAmount") BigDecimal amount) {
        final Balance balance = new Balance();
        balance.setAmount(amount);
        return balanceService.save(balance);
    }
}
