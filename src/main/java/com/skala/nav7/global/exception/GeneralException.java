package com.skala.nav7.global.exception;


import com.skala.nav7.global.apiPayload.code.base.BaseErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final BaseErrorCode baseErrorCode;

    public GeneralException(BaseErrorCode baseErrorCode) {
        this.baseErrorCode = baseErrorCode;
    }
}
