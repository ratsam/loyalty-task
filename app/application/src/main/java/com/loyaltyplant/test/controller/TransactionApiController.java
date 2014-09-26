package com.loyaltyplant.test.controller;

import com.loyaltyplant.test.service.BalanceTransferService;
import com.loyaltyplant.test.service.OrderProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Controller
@RequestMapping("/api/transaction")
public class TransactionApiController {

    @Autowired
    private BalanceTransferService balanceTransferService;

    @RequestMapping(value = "/debit", method = RequestMethod.POST)
    @ResponseBody
    public DebitResult debit(@Valid DebitCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badDebitRequest(command, bindingResult);
        } else {
            return doDebit(command);
        }
    }

    private DebitResult badDebitRequest(DebitCommand command, BindingResult bindingResult) {
        final DebitResult result = new DebitResult();
        result.setCommand(command);
        result.setSuccess(false);
        // TODO: set error message from bindingResult
        return result;
    }

    private DebitResult doDebit(DebitCommand command) {
        try {
            balanceTransferService.debit(command.getFrom(), command.getAmount());
            return successDebit(command);
        } catch (OrderProcessingException e) {
            return failedDebit(command);
        }
    }

    private DebitResult successDebit(DebitCommand command) {
        final DebitResult result = new DebitResult();
        result.setCommand(command);
        result.setSuccess(true);
        return result;
    }

    private DebitResult failedDebit(DebitCommand command) {
        final DebitResult result = new DebitResult();
        result.setCommand(command);
        result.setSuccess(false);
        result.setErrorMessage("Debit operation failed. Please check balance.");
        return result;
    }

    @RequestMapping(value = "/credit", method = RequestMethod.POST)
    @ResponseBody
    public CreditResult credit(@Valid CreditCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badCreditRequest(command, bindingResult);
        } else {
            return doCredit(command);
        }
    }

    private CreditResult badCreditRequest(CreditCommand command, BindingResult bindingResult) {
        final CreditResult result = new CreditResult();
        result.setCommand(command);
        result.setSuccess(false);
        // TODO: set error message from bindingResult
        return result;
    }

    private CreditResult doCredit(CreditCommand command) {
        try {
            balanceTransferService.credit(command.getTo(), command.getAmount());
            return successCredit(command);
        } catch (OrderProcessingException e) {
            return failedCredit(command);
        }
    }

    private CreditResult successCredit(CreditCommand command) {
        final CreditResult result = new CreditResult();
        result.setCommand(command);
        result.setSuccess(true);
        return result;
    }

    private CreditResult failedCredit(CreditCommand command) {
        final CreditResult result = new CreditResult();
        result.setCommand(command);
        result.setSuccess(false);
        result.setErrorMessage("Credit operation failed.");
        return result;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ResponseBody
    public TransferResult transfer(@Valid TransferCommand command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badTransferRequest(command, bindingResult);
        } else {
            return doTransfer(command);
        }
    }

    private TransferResult badTransferRequest(TransferCommand command, BindingResult bindingResult) {
        final TransferResult result = new TransferResult();
        result.setCommand(command);
        result.setSuccess(false);
        // TODO: error message from bindingResult
        return result;
    }

    private TransferResult doTransfer(TransferCommand command) {
        try {
            balanceTransferService.transfer(command.getFrom(), command.getTo(), command.getAmount());
            return transferSucceed(command);
        } catch (OrderProcessingException e) {
            return transferFailed(command);
        }
    }

    private TransferResult transferSucceed(TransferCommand command) {
        final TransferResult result = new TransferResult();
        result.setCommand(command);
        result.setSuccess(true);
        return result;
    }

    private TransferResult transferFailed(TransferCommand command) {
        final TransferResult result = new TransferResult();
        result.setCommand(command);
        result.setSuccess(false);
        result.setErrorMessage("Transfer failed. Please check balance.");
        return result;
    }
}
