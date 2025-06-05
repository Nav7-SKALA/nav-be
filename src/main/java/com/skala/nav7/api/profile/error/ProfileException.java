package com.skala.nav7.api.profile.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class ProfileException extends GeneralException {
    public ProfileException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
