package com.skala.nav7.api.role.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class RoleException extends GeneralException {
    public RoleException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
