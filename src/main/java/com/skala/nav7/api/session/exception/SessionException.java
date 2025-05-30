package com.skala.nav7.api.session.exception;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class SessionException extends GeneralException {
    public SessionException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
