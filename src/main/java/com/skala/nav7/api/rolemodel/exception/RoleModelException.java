package com.skala.nav7.api.rolemodel.exception;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class RoleModelException extends GeneralException {
    public RoleModelException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
