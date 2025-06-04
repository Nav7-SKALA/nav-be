package com.skala.nav7.global.auth.cookie.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class CookieException extends GeneralException {
    public CookieException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
