package org.wso2.carbon.privacy.forgetme.userstore.exception;

import org.wso2.carbon.privacy.forgetme.api.runtime.InstructionExecutionException;

/**
 * The exception class which is used to raise exception conditions during user store instruction execution.
 */
public class UserStoreInstructionExecutionException extends InstructionExecutionException {

    public UserStoreInstructionExecutionException(String message) {

        super(message);
    }

    public UserStoreInstructionExecutionException(String message, Throwable throwable) {

        super(message, throwable);
    }
}
