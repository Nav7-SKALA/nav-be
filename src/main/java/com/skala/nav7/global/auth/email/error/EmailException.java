package com.skala.nav7.global.auth.email.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class EmailException extends GeneralException {
    public EmailException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
