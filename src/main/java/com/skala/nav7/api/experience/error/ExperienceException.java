package com.skala.nav7.api.experience.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class ExperienceException extends GeneralException {

    public ExperienceException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
