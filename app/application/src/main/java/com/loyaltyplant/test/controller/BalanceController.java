package com.loyaltyplant.test.controller;

import com.loyaltyplant.test.domain.Balance;
import com.loyaltyplant.test.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Controller
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getBalances(Model model) {
        return getBalances(model, new PageRequest(0, 20));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, params = "page")
    public String getBalances(Model model, Pageable pageRequest) {
        model.addAttribute("balancesPage", balanceService.findAll(pageRequest));
        return "balances";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createBalance(@RequestParam("initialAmount") BigDecimal amount) {
        // TODO: validate amount is non-negative
        final Balance balance = new Balance();
        balance.setAmount(amount);
        balanceService.save(balance);

        return "redirect:/balance/";
    }
}
