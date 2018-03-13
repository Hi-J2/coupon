package com.kakao.pay.coupon.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
	Y("Y", "사용"),
	N("N", "미사용");

	private String code;
	private String value;
}
