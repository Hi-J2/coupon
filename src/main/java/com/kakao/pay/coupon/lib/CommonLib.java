package com.kakao.pay.coupon.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonLib {
	public static boolean isEmailFormat(String email) {
		if (null != email) {
			String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

}
