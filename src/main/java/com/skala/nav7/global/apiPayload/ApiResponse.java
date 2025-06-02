package com.skala.nav7.global.apiPayload;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.skala.nav7.global.apiPayload.code.SuccessCode;
import com.skala.nav7.global.apiPayload.code.base.BaseCode;
import com.skala.nav7.global.apiPayload.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private T result;

    //성공시
    public static <T> ApiResponse<T> onSuccess(T result) {
        ReasonDTO dto = SuccessCode._OK.getReasonHttpStatus();
        return ApiResponse.<T>builder()
                .isSuccess(true)
                .code(dto.getCode())
                .message(dto.getMessage())
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> onSuccess(BaseCode code) {
        ReasonDTO dto = code.getReasonHttpStatus();
        return ApiResponse.<T>builder()
                .isSuccess(true)
                .code(dto.getCode())
                .message(dto.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> of(BaseCode code, T result) {
        return ApiResponse.<T>builder()
                .isSuccess(true)
                .code(code.getReasonHttpStatus().getCode())
                .message(code.getReasonHttpStatus().getMessage())
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> onFailure(String code, String message, T result) {
        return ApiResponse.<T>builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> onFailure(String code, String message) {
        return ApiResponse.<T>builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

}