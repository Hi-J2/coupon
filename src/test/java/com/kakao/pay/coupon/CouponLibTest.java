package com.kakao.pay.coupon;

import com.kakao.pay.coupon.lib.Base62;
import com.kakao.pay.coupon.lib.CouponLib;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CouponLibTest {


	/**
	 * 배열의 왼쪽으로 CycleRotation 테스트
	 */
	@Test
	public void leftCyclicRotationTest() {
		String rotationStr = CouponLib.leftCyclicRotation("ABCDEFG", 3);
		Assert.assertEquals("DEFGABC", rotationStr);

		rotationStr = CouponLib.leftCyclicRotation("ABCDEFGHIJK", 1000);
		Assert.assertEquals("KABCDEFGHIJ", rotationStr);
	}

	/**
	 * 배열의 오른쪽  CycleRotation 테스트
	 */
	@Test
	public void rightCyclicRotationTest() {
		String rotationStr = CouponLib.rightCyclicRotation("DEFGABC", 3);
		Assert.assertEquals("ABCDEFG", rotationStr);

		rotationStr = CouponLib.rightCyclicRotation("KABCDEFGHIJ", 1000);
		Assert.assertEquals("ABCDEFGHIJK", rotationStr);
	}

	/**
	 * ZeroFill Test
	 * ex) INPUT : (a,4) OUTPUT : 0000a
	 */
	@Test
	public void zeroFillTest() {
		String zeroFillStr = CouponLib.fillZero("TEST", 2);
		Assert.assertEquals("00TEST", zeroFillStr);

		zeroFillStr = CouponLib.fillZero("a", 3);
		Assert.assertEquals("000a", zeroFillStr);

		zeroFillStr = CouponLib.fillZero("AAAAAA", 3);
		Assert.assertEquals("000AAAAAA", zeroFillStr);
	}

	/**
	 * 쿠폰 코드 추출 테스트
	 */
	@Test
	public void decodeCodeTest() {
		List<String> testStrList = Arrays.asList("j0v0i1G0F0K04M41",
				"a2o0O0p070X0nN3Z",
				"s0C0k020f0c3Zm3c",
				"A0W0u4c0T0C0dp4j",
				"o0h0p085L0z02O3m");

		String code;

		code = CouponLib.decodeCode(testStrList.get(0));
		Assert.assertEquals("jviGFK4M", code);

		code = CouponLib.decodeCode(testStrList.get(1));
		Assert.assertEquals("aoOp7XnN", code);

		code = CouponLib.decodeCode(testStrList.get(2));
		Assert.assertEquals("sCk2fcZm", code);

		code = CouponLib.decodeCode(testStrList.get(3));
		Assert.assertEquals("AWucTCdp", code);

		code = CouponLib.decodeCode(testStrList.get(4));
		Assert.assertEquals("ohp8Lz2O", code);
	}


	/**
	 * HashCode 유효성 테스트
	 */
	@Test
	public void hashCodeTest() {
		List<String> testStrList = Arrays.asList("j0v0i1G0F0K04M41",
				"a2o0O0p070X0nN3Z",
				"s0C0k020f0c3Zm3c",
				"A0W0u4c0T0C0dp4j",
				"o0h0p085L0z02O3m");


		int hashCode;
		String code;
		String hashCodeBase62;

		code = testStrList.get(0);
		hashCodeBase62 = code.substring(code.length() - 2, code.length());
		hashCode = Base62.toBase10(hashCodeBase62);
		Assert.assertEquals(249, hashCode);

		code = testStrList.get(1);
		hashCodeBase62 = code.substring(code.length() - 2, code.length());
		hashCode = Base62.toBase10(hashCodeBase62);
		Assert.assertEquals(247, hashCode);

		code = testStrList.get(2);
		hashCodeBase62 = code.substring(code.length() - 2, code.length());
		hashCode = Base62.toBase10(hashCodeBase62);
		Assert.assertEquals(198, hashCode);


		code = testStrList.get(3);
		hashCodeBase62 = code.substring(code.length() - 2, code.length());
		hashCode = Base62.toBase10(hashCodeBase62);
		Assert.assertEquals(267, hashCode);

		code = testStrList.get(4);
		hashCodeBase62 = code.substring(code.length() - 2, code.length());
		hashCode = Base62.toBase10(hashCodeBase62);
		Assert.assertEquals(208, hashCode);

	}

	/**
	 * 유효성 라이브러리 테스트
	 */

	@Test
	public void invalidHashCodeTest() {
		List<String> testStrList = Arrays.asList("j0v0i1G0F0K04M41",
				"a2o0O0p070X0nN3Z",
				"s0C0k020f0c3Zm3c",
				"A0W0u4c0T0C0dp4j",
				"o0h0p085L0z02O3m");

		for (int i = 0; i < testStrList.size(); i++) {
			Assert.assertTrue(CouponLib.isInvalidCode(testStrList.get(i)));
		}
		String code = "o0h0p085L0z02O3g";
		Assert.assertFalse(CouponLib.isInvalidCode(code));

		code = "o0h0p085L0z02Off";
		Assert.assertFalse(CouponLib.isInvalidCode(code));

	}


	/**
	 * SEQ(id) 복원 테스트
	 */
	@Test
	public void decodeSeqTest() {
		List<String> testStrList = Arrays.asList("j0v0i1G0F0K04M41",
				"a2o0O0p070X0nN3Z",
				"s0C0k020f0c3Zm3c",
				"A0W0u4c0T0C0dp4j",
				"o0h0p085L0z02O3m");
		for (int i = 0; i < testStrList.size(); i++) {
			String code = testStrList.get(i);
			String base62HashCode = code.substring(code.length() - 2, code.length());

			int hashCode = Base62.toBase10(base62HashCode);
			String rotationSeq = CouponLib.decodeSeq(code);
			String bas62Seq = CouponLib.leftCyclicRotation(rotationSeq, hashCode);

			int seq = Base62.toBase10(bas62Seq);

			Assert.assertEquals(i + 1, seq);
		}
	}

	/**
	 * SEQ(id) 라이브러리 복원 테스트
	 */
	@Test
	public void getSeqTest() {
		List<String> testStrList = Arrays.asList("j0v0i1G0F0K04M41",
				"a2o0O0p070X0nN3Z",
				"s0C0k020f0c3Zm3c",
				"A0W0u4c0T0C0dp4j",
				"o0h0p085L0z02O3m");
		for (int i = 0; i < testStrList.size(); i++) {

			long seq = CouponLib.restoreSeq(testStrList.get(i));

			Assert.assertEquals(i + 1, seq);
		}
	}
}
