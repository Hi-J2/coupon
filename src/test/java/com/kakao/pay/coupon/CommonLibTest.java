package com.kakao.pay.coupon;

import com.kakao.pay.coupon.lib.CommonLib;
import com.kakao.pay.coupon.lib.CouponLib;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CommonLibTest {

	/**
	 * Email 형식인지 체크
	 */
	@Test
	public void isEmailFormatTest() {
		String email = "kani@kanistyle.com";
		Assert.assertTrue(CommonLib.isEmailFormat(email));

		email = "kani@kanistyle";
		Assert.assertFalse(CommonLib.isEmailFormat(email));
	}

}
