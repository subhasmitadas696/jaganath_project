package com.csmtech.SJTA.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidator {
	public static boolean isEmpty(String data) {
		return (data == null || data.isEmpty() || data.trim().isEmpty());
	}

	public static boolean minLengthCheck(String data, int minLength) {
		return (data.length() > 0 && data.length() < minLength);
	}

	public static boolean maxLengthCheck(String data, int maxLength) {
		return (data.length() > 0 && data.length() > maxLength);
	}

	public static boolean isCharecterKey(String charKey) {
		String charKeyRegex = "^[A-Za-z ]+$";
		Pattern pattern = Pattern.compile(charKeyRegex);
		Matcher matcher = pattern.matcher(charKey);
		// return matcher.matches();
		Boolean status = false;
		if (matcher.matches()) {

			System.out.println("Given number is Character");
		} else {
			status = true;
			System.out.println("Given number is not Character");
		}
		return status;

	}

	public static boolean isNumericKey(String numbeKey) {
		String numbeKeyRegex = "^[0-9]+$";
		Pattern pattern = Pattern.compile(numbeKeyRegex);
		Matcher matcher = pattern.matcher(numbeKey);
		// return matcher.matches();
		Boolean status = false;
		if (matcher.matches()) {

			System.out.println("Given number is Numeric");
		} else {
			status = true;
			System.out.println("Given number is not Numeric");
		}
		return status;

	}

	public static boolean isAlphaNumericKey(String alphaNumKey) {
		String alphaNumKeyRegex = "^[0-9a-zA-Z @.-/,]*$";
		Pattern pattern = Pattern.compile(alphaNumKeyRegex);
		Matcher matcher = pattern.matcher(alphaNumKey);
		Boolean status = false;
		if (matcher.matches()) {

			System.out.println("Given number is AlphaNumeric");
		} else {
			status = true;
			System.out.println("Given number is not AlphaNumeric");
		}
		return status;
	}

	public static boolean isDecimal(String decimalKey) {
		String decimalKeyRegex = "^\\d+?\\.\\d+?$";
		Pattern pattern = Pattern.compile(decimalKeyRegex);
		Matcher matcher = pattern.matcher(decimalKey);
		Boolean status = false;
		if (matcher.matches()) {

			System.out.println("Given number is Decimal");
		} else {
			status = true;
			System.out.println("Given number is not Decimal");
		}
		return status;
	}

	public static boolean emailCheck(String emailId) {
		String emailRegex = "^([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(emailId);
		// return matcher.matches();
		Boolean status = false;
		if (matcher.matches()) {
			System.out.println("Given email id is valid");
		} else {
			status = true;
			System.out.println("Given email id is not valid");
		}
		return status;

	}

	public static boolean isSpecialCharKey(String text) {
		String textRegx = "[^&%$#@!~]*";
		Pattern pattern = Pattern.compile(textRegx);
		Matcher matcher = pattern.matcher(text);
		// return matcher.matches();
		Boolean status = false;
		if (matcher.matches()) {
			System.out.println("Given text  is valid");
		} else {
			status = true;
			System.out.println("Given text  is not valid");
		}
		return status;

	}

	public static boolean validNumberCheck(String numbeKey) {
		String numbeKeyRegex = "^[0-9]+$";
		Pattern pattern = Pattern.compile(numbeKeyRegex);
		Matcher matcher = pattern.matcher(numbeKey);
		// return matcher.matches();
		Boolean status = false;
		if (matcher.matches()) {

			System.out.println("Given number is Numeric");
		} else {
			status = true;
			System.out.println("Given number is not Numeric");
		}
		return status;

	}

	public static boolean validTelCheck(String mobile) {
		String mobileRegex = "(0/91)?[7-9][0-9]{9}";
		Pattern pattern = Pattern.compile(mobileRegex);
		Matcher matcher = pattern.matcher(mobile);
		// return matcher.matches();
		Boolean status = false;
		if (matcher.matches()) {

			System.out.println("Given Telephone is Numeric");
		} else {
			status = true;
			System.out.println("Given Telephone is not Numeric");
		}
		return status;

	}

	public static boolean validPwdCheck(String password) {
		String passwordRegex = "^.*(?=.{8,15})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&!%()*?]).*$";

		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(password);
		// return matcher.matches();
		Boolean status = false;
		if (matcher.matches()) {

			System.out.println("Given Password is Numeric");
		} else {
			status = true;
			System.out.println("Given Password is not Numeric");
		}
		return status;

	}

	public static boolean isUrl(String url) {
		String urlRegex = "^(?:(?:https?|ftp):\\/\\/)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))(?::\\d{2,5})?(?:\\/\\S*)?$";
		Pattern pattern = Pattern.compile(urlRegex);
		Matcher matcher = pattern.matcher(url);
		return matcher.matches();
	}

	public static boolean isPassword(String password) {
		String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,15}$";
		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	public static boolean BlankCheckRdoDropChk(String data) {
		return (data == null || data.isEmpty() || data.trim().isEmpty()) || data.equals("0");
	}

	public static boolean validateFile(String str) {
		String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp|pdf|doc|docx|xls|jpg|csv|dbf|htm|html|mht|mhtml|ods|prn|txt|xla|xlam|xlsb|xlsx|xlt|xltm|xlsm|xlw|xps|docm|dot|dotm|dotx|odt|rtf|wps|xml|msword|mp4|ogx|oga|ogv|ogg|webm|mp3|mpeg|zip))$)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		Boolean status = false;
		if (matcher.matches()) {

			System.out.println("Given file is Valid");
		} else {
			status = true;
			System.out.println("Given file is  Invalid ");
		}
		return status;

	}
}
