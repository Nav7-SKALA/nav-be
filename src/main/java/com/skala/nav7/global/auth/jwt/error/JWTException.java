package com.skala.nav7.global.auth.jwt.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class JWTException extends GeneralException {
    public JWTException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
