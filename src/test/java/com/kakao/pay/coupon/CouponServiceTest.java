package com.kakao.pay.coupon;


import com.kakao.pay.coupon.common.StatusEnum;
import com.kakao.pay.coupon.entity.Coupon;
import com.kakao.pay.coupon.service.CouponService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponServiceTest {

	@Autowired
	CouponService couponService;

	/**
	 * 쿠폰코드가 중복되서 발행이 되는지 테스트 100개
	 */
	@Test
	public void couponCreateTest() {
		String emailPrefix = "test";
		String emailPostFix = "@test.com";

		List<String> codeList = new ArrayList<>();

		for (int i = 1; i <= 100; i++) {
			String email = emailPrefix + String.valueOf(i) + emailPostFix;
			Coupon coupon = new Coupon();
			coupon.setEmail(email);
			codeList.add(couponService.create(coupon).getCode());
		}

		long count = codeList.stream().distinct().count();

		Assert.assertEquals(count, codeList.size());
	}

	/**
	 * 페이징 테스트
	 */
	@Test
	public void couponPageableTest() {
		String emailPrefix = "test";
		String emailPostFix = "@pageble.com";

		List<String> codeList = new ArrayList<>();

		for (int i = 1; i <= 50; i++) {
			String email = emailPrefix + String.valueOf(i) + emailPostFix;
			Coupon coupon = new Coupon();
			coupon.setEmail(email);
			codeList.add(couponService.create(coupon).getCode());
		}

		Pageable pageable = new PageRequest(0, 10);
		Page<Coupon> page = couponService.findAll(pageable);
		Assert.assertEquals(page.getSize(), 10);

		pageable = new PageRequest(1, 20);
		page = couponService.findAll(pageable);
		Assert.assertEquals(page.getSize(), 20);

	}

	/**
	 * 쿠폰 사용테스트
	 */
	@Test
	public void couponUseTest() {

		String emailPrefix = "test";
		String emailPostFix = "@use.com";

		List<String> codeList = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			String email = emailPrefix + String.valueOf(i) + emailPostFix;
			Coupon coupon = new Coupon();
			coupon.setEmail(email);
			codeList.add(couponService.create(coupon).getCode());
		}

		Coupon coupon = couponService.findOne(1L);
		coupon = couponService.useCoupon(coupon.getCode());
		Assert.assertEquals(coupon.getStatus(), StatusEnum.Y);

		coupon = couponService.findOne(2L);
		coupon = couponService.useCoupon(coupon.getCode());
		Assert.assertEquals(coupon.getStatus(), StatusEnum.Y);
	}
}
