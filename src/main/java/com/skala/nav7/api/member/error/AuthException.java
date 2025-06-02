package com.skala.nav7.api.member.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class AuthException extends GeneralException {
    public AuthException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
