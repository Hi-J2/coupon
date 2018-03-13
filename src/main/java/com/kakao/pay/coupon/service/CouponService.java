package com.kakao.pay.coupon.service;

import com.kakao.pay.coupon.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponService {

	Page<Coupon> findAll(Pageable pageable);

	Coupon findOne(Long id);

	Coupon create(Coupon coupon);

	Coupon useCoupon(String code);
}
