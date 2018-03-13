package com.kakao.pay.coupon.dto;

import com.kakao.pay.coupon.common.ErrorCode;
import com.kakao.pay.coupon.exception.ApiException;
import lombok.Data;

@Data
public class ApiErrorResponse {

    String code;
    String message;

    public ApiErrorResponse(ApiException ae) {
        this.code = ae.getCode().getCode();
        this.message = ae.getCode().getMessage();
    }

    public ApiErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
