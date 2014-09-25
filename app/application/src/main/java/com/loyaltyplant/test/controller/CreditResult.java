package com.loyaltyplant.test.controller;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class CreditResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private CreditCommand command;

    private boolean success;

    @Nullable
    private String errorMessage;

    public CreditCommand getCommand() {
        return command;
    }

    public void setCommand(CreditCommand command) {
        this.command = command;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(@Nullable String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
