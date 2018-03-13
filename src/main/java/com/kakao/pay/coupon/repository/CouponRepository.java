package com.kakao.pay.coupon.repository;

import com.kakao.pay.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	Coupon findOneByEmail(String email);
}
