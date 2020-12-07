package com.app.toolboxlibrary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码校验类
 * @author
 *
 */
public class PasswordCheckUtil
{
	public static final int NULL_PWD = 0;
	public static final int WEAK_PWD = 1;
	public static final int MODERATE_PWD = 2;
	public static final int STRENGH_PWD = 3;

	 /**
     * 采用正则表达式检查密码强度
    */
	public static int checkPassword(String passwordStr) {
//		输入单数字或英文或符号 密码强度显示 弱
//		输入数字、英文、符号中的任意2种 密码强度显示 中
//		输入数字、英文、符号 三种 密码强度显示 强
		final String str1 = "[0-9]{1,20}$"; // 不超过20位的数字组合
		final String str2 = "^[a-zA-Z]{1,20}$"; // 由字母不超过20位
		final String str3 = "^[0-9|a-z|A-Z]{1,20}$"; // 由字母、数字组成，不超过20位
		final String str4 = "^[0-9|a-z|A-Z|[^0-9|^a-z|^A-Z]]{1,20}$"; // 由字母、数字、符号 三种组成，不超过20位

		if(passwordStr==null || passwordStr.length()==0){
			return WEAK_PWD;
		}
		if (passwordStr.matches(str1) || passwordStr.matches(str2)) {
			return WEAK_PWD;
		}
		if (passwordStr.matches(str3)) {
			return MODERATE_PWD;
		}
		if (passwordStr.matches(str4)) {
			return STRENGH_PWD;
		}
		return STRENGH_PWD;

	}

	/**
	 * 采用正则表达式检查密码强度
	 */
	public static int NewCheckPassword(String passwordStr) {
//		输入单数字或英文或符号 密码强度显示 弱
//		输入数字、英文、符号中的任意2种 密码强度显示 中
//		输入数字、英文、符号 三种 密码强度显示 强
		final String str1 = "^[0-9]{1,20}$"; // 不超过20位的数字组合
		final String str2 = "^[a-zA-Z]{1,20}$"; // 不超过20位的字母组合
		final String str3 = "^[^a-zA-Z|^0-9]{1,20}$"; // 不超过20位的特殊符号组合

		//final String str2 = "^[a-zA-Z]{1,20}$"; // 由字母不超过20位
		//final String str3 = "^[0-9|a-z|A-Z]{1,20}$"; // 由字母、数字组成，不超过20位
		final String str4 = "[0-9]{1,20}[a-z|A-Z]{1,20}[^0-9|^a-z|^A-Z]{1,20}"; // 由字母、数字、符号 三种组成，不超过20位

		if(passwordStr==null || passwordStr.length()==0){
			return NULL_PWD;
		}
		if (passwordStr.matches(str1) || passwordStr.matches(str2)
				|| passwordStr.matches(str3) ) {
			return WEAK_PWD;
		}
//		if (passwordStr.matches(str3)) {
//			return MODERATE_PWD;
//		}
//		if (passwordStr.matches(str4)) {
//			return STRENGH_PWD;
//		}
		if (passwordStr.matches("^.*[a-zA-Z]+.*$") && passwordStr.matches("^.*[0-9]+.*$")
				&& passwordStr.matches("^.*[^0-9|^a-z|^A-Z]+.*$")) {
			return STRENGH_PWD;
		}
		return MODERATE_PWD;

	}

	/**
	 * 过滤特殊字符，只能输入字母数字
	 *
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		// 只允许字母、数字、特殊符号
		String regEx = "[^a-zA-Z0-9|^[^a-zA-Z|^0-9]]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

}