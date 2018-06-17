package org.wso2.carbon.privacy.forgetme.userstore.exception;

import org.wso2.carbon.privacy.forgetme.api.runtime.ModuleException;

/**
 * The exception class which is used to raise exception conditions the user store module in general.
 */
public class UserStoreModuleException extends ModuleException {

    public UserStoreModuleException(String message) {

        super(message);
    }

    public UserStoreModuleException(Throwable throwable) {

        super(throwable);
    }

    public UserStoreModuleException(String message, Throwable throwable) {

        super(message, throwable);
    }

}
