package com.csmtech.SJTA.util;

import java.util.Random;

public class OtpGenerateCommonUtil {

	public static String generateOTP() {
		Integer length = 6;
		StringBuilder otp = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			int digit = random.nextInt(10);
			otp.append(digit);
		}
		return otp.toString();
	}
}
