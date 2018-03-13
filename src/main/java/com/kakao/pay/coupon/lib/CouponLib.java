package com.kakao.pay.coupon.lib;

import java.util.Arrays;
import java.util.Random;

public class CouponLib {
	public static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int BASE = ALPHABET.length();

	// 생성할 코드 길이
	static final int GENRATE_COUPON_LENGTH = 8;

	// 62진수 최대 Integer 길이
	static final int ID_LENGTH = 6;

	public static String fillZero(String str, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(0);
		}
		return sb.append(str).toString();
	}

	public static int[] generateSeed(int length) {
		int[] seed = new int[length];
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			seed[i] = random.nextInt(BASE);
		}
		return seed;
	}


	public static String fromBase10(int i) {
		StringBuilder sb = new StringBuilder("");
		if (i == 0) {
			return "a";
		}
		while (i > 0) {
			i = fromBase10(i, sb);
		}
		return sb.reverse().toString();
	}

	private static int fromBase10(int i, final StringBuilder sb) {
		int rem = i % BASE;
		sb.append(ALPHABET.charAt(rem));
		return i / BASE;
	}

	public static int toBase10(String str) {
		return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
	}

	private static int toBase10(char ch) {
		return toBase10(ALPHABET.indexOf(ch), 0);
	}

	private static int toBase10(char[] chars) {
		int n = 0;
		for (int i = chars.length - 1; i >= 0; i--) {
			n += toBase10(ALPHABET.indexOf(chars[i]), i);
		}
		return n;
	}

	private static int toBase10(int n, int pow) {
		return n * (int) Math.pow(BASE, pow);
	}

	public static String generateCode(int seq) {
		int[] seed = generateSeed(GENRATE_COUPON_LENGTH);

		int sum = Arrays.stream(seed).sum();

		String hashCode = fromBase10(sum);
		if (hashCode.length() < 2) {
			hashCode = CouponLib.fillZero(hashCode, 2 - hashCode.length());
		}

		String id = fromBase10(seq);
		String fillZeroId = fillZero(id, ID_LENGTH - id.length());
		String rotatedId = rightCyclicRotation(fillZeroId, sum);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < GENRATE_COUPON_LENGTH; i++) {
			sb.append(ALPHABET.charAt(seed[i]));
			if (i < rotatedId.length()) {
				sb.append(rotatedId.charAt(i));
			}
		}

		sb.append(hashCode);
		return sb.toString();
	}

	public static String restoreSeq(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < GENRATE_COUPON_LENGTH + ID_LENGTH - 1; i += 2) {
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}

	public static String restoreCode(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length() - 2; i++) {
			if (i % 2 == 0 || GENRATE_COUPON_LENGTH + ID_LENGTH - 2 < i) {
				sb.append(str.charAt(i));
			}

		}
		return sb.toString();
	}

	public static boolean isInvalidCode(String code) {
		String restoreCode = restoreCode(code);
		String hashStr = code.substring(GENRATE_COUPON_LENGTH + ID_LENGTH, code.length());
		int hashcode = CouponLib.toBase10(hashStr);

		int sum = 0;
		for (int i = 0; i < restoreCode.length(); i++) {
			sum += toBase10(restoreCode.charAt(i));
		}
		return hashcode == sum;
	}

	public static long getSeq(String code){
		String base62HashCode = code.substring(code.length() - 2, code.length());
		int hashCode = toBase10(base62HashCode);
		String rotationSeq = restoreSeq(code);
		String bas62Seq = leftCyclicRotation(rotationSeq, hashCode);

		return toBase10(bas62Seq);
	}

	public static String rightCyclicRotation(String str, int count) {
		int length = str.length();

		if (count < 1 || length < 1) {
			return str;
		}

		count = count % length;
		char[] rotationStr = str.toCharArray();
		char buffer;

		for (int i = 0; i < count; i++) {
			for (int j = 0; j < length; j++) {
				buffer = rotationStr[0];
				rotationStr[0] = rotationStr[j];
				rotationStr[j] = buffer;
			}
		}
		return new StringBuffer().append(rotationStr).toString();
	}


	public static String leftCyclicRotation(String str, int count) {
		int length = str.length();

		if (count < 1 || length < 1) {
			return str;
		}

		count = count % length;
		char[] rotationStr = str.toCharArray();
		char buffer;

		for (int i = 0; i < count; i++) {
			for (int j = length - 1; j >= 0; j--) {
				buffer = rotationStr[length - 1];
				rotationStr[length - 1] = rotationStr[j];
				rotationStr[j] = buffer;
			}
		}
		return new StringBuffer().append(rotationStr).toString();
	}

}
