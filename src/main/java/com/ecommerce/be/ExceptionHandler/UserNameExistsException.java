
package com.ecommerce.be.ExceptionHandler;

import com.ecommerce.be.Constant.ErrorMessage;

public class UserNameExistsException extends RuntimeException {
    public UserNameExistsException() {
        super(ErrorMessage.USER_ALREADY_EXIST);
    }
}