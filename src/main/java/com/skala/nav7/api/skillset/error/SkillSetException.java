package com.skala.nav7.api.skillset.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class SkillSetException extends GeneralException {
    public SkillSetException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
