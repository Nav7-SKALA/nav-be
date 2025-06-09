package com.skala.nav7.api.project.error;

import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import com.skala.nav7.global.exception.GeneralException;

public class ProjectException extends GeneralException {
    public ProjectException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
