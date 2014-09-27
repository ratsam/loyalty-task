package com.loyaltyplant.test.controller;

import com.loyaltyplant.test.service.BalanceTransferService;
import com.loyaltyplant.test.service.OrderProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Controller
@RequestMapping("/transfer")
public class TransactionController {

    @Autowired
    private BalanceTransferService balanceTransferService;

    @RequestMapping("/")
    public String index() {
        return "transactions";
    }

    @RequestMapping(value = "/debit", method = RequestMethod.POST)
    public String debit(@Valid DebitCommand debitCommand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "transactions";
        } else {
            return doDebit(debitCommand, bindingResult, model);
        }
    }

    private String doDebit(DebitCommand debitCommand, BindingResult bindingResult, Model model) {
        try {
            balanceTransferService.debit(debitCommand.getFrom(), debitCommand.getAmount());
        } catch (OrderProcessingException e) {
            bindingResult.rejectValue("amount", "loyalty.funds.insufficient");
            return "transactions";
        }

        return "redirect:/transfer/";
    }

    @RequestMapping(value = "/credit", method = RequestMethod.POST)
    public String credit(@Valid CreditCommand creditCommand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "transactions";
        } else {
            return doCredit(creditCommand, bindingResult, model);
        }
    }

    private String doCredit(CreditCommand creditCommand, BindingResult bindingResult, Model model) {
        try {
            balanceTransferService.credit(creditCommand.getTo(), creditCommand.getAmount());
        } catch (OrderProcessingException e) {
            bindingResult.rejectValue("amount", "loyalty.credit.failed");
            return "transactions";
        }

        return "redirect:/transfer/";
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String transfer(@Valid TransferCommand transferCommand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "transactions";
        } else {
            return doTransfer(transferCommand, bindingResult, model);
        }
    }

    private String doTransfer(TransferCommand transferCommand, BindingResult bindingResult, Model model) {
        try {
            balanceTransferService.transfer(transferCommand.getFrom(), transferCommand.getTo(), transferCommand.getAmount());
        } catch (OrderProcessingException e) {
            bindingResult.rejectValue("amount", "loyalty.funds.insufficient");
            return "transactions";
        }

        return "redirect:/transfer/";
    }

    @ModelAttribute
    public TransferCommand emptyTransferCommand() {
        return new TransferCommand();
    }

    @ModelAttribute
    public CreditCommand emptyCreditCommand() {
        return new CreditCommand();
    }

    @ModelAttribute
    public DebitCommand emptyDebitCommand() {
        return new DebitCommand();
    }
}
