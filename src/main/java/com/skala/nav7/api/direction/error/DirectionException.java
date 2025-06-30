package com.skala.nav7.api.direction.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class DirectionException extends GeneralException {

    public DirectionException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
