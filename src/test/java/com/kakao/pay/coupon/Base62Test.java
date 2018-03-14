package com.kakao.pay.coupon;

import com.kakao.pay.coupon.lib.Base62;
import org.junit.Assert;
import org.junit.Test;

public class Base62Test {

	/**
	 * 10진수를 62진수로
	 */
	@Test
	public void fromBase62() {
		int number = 10;
		String base62Number = Base62.fromBase10(number);
		Assert.assertEquals("a", base62Number);

		number = 61;
		base62Number = Base62.fromBase10(number);
		Assert.assertEquals("Z", base62Number);
	}

	/**
	 * 62진수를 10진수로
	 */
	@Test
	public void toBase10() {

		String base62Number = "a";
		int number = Base62.toBase10(base62Number);
		Assert.assertEquals(10, number);

		base62Number = "Z";
		number = Base62.toBase10(base62Number);
		Assert.assertEquals(61, number);
	}

}
