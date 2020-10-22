package com.recruiting.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static boolean validate(String emailStr,String pattern) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
}
