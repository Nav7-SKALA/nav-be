package com.skala.nav7.api.session.exception;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class FastAPIException extends GeneralException {
    public FastAPIException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
