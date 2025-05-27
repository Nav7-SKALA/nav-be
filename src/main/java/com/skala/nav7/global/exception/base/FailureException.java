package com.skala.nav7.global.exception.base;


import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class FailureException extends GeneralException {

    public FailureException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
