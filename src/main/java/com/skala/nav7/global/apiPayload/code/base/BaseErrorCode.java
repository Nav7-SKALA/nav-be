package com.skala.nav7.global.apiPayload.code.base;


import com.skala.nav7.global.apiPayload.dto.ErrorReasonDTO;

public interface BaseErrorCode {

    ErrorReasonDTO getReasonHttpStatus();
}