package com.skala.nav7.api.certification.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class CertificationException extends GeneralException {

    public CertificationException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
