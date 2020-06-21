package com.wechat.commons.utils;

import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * ~{OnD?C{3F#:~}RenRenERP <br />
 * ~{@`C{3F#:~}StringUtils <br />
 * ~{44=(HK#:~}Administrator <br />
 * ~{18W"#:~} <br />
 * 
 * @version <br />
 */
public class StringUtil extends org.apache.commons.lang.StringUtils
{
	public static boolean isNullOrBlank(Object object)
	{
		if (object == null)
			return true;

		return trimToEmpty(String.valueOf(object)).length() == 0;
	}

	public static boolean isAnyBlank(String... string)
	{
		for (String s : string)
		{
			if (isBlank(s))
				return true;
		}

		return false;
	}

	public static boolean isAllBlank(String... string)
	{
		for (String s : string)
		{
			if (!isBlank(s))
				return false;
		}

		return true;
	}

	public static boolean equalsAny(String value, String... string)
	{
		for (String s : string)
		{
			if (equals(value, s))
				return true;
		}

		return false;
	}

	public static boolean equalsAny(String value, List<String> list)
	{
		for (String s : list)
		{
			if (equals(value, s))
				return true;
		}

		return false;
	}

	public static int notEmptyCount(String... string)
	{
		int count = 0;
		for (String s : string)
		{
			if (isNotEmpty(s))
				count++;
		}

		return count;
	}
	
	public static boolean isEmpty(String... strings) {
		if (strings == null || strings.length == 0) {
			return true;
		}
		for (String string : strings) {
			if (string == null || "".equals(string.trim())) {
				return true;
			}
		}
		return false;
	}

	public static String substringLast(String string, int length)
	{
		if (isEmpty(string))
			return EMPTY;

		return substring(string, string.length() - length);
	}

	public static String upperCaseFirst(String string)
	{
		if (isEmpty(string))
			return EMPTY;

		char character = Character.toUpperCase(string.charAt(0));
		String substring = substringLast(string, string.length() - 1);
		return String.format("%s%s", character, substring);
	}

	public static String parse(Object object)
	{
		return parse(object, true);
	}

	public static String parse(Object object, boolean trim)
	{
		if (object == null)
			return EMPTY;

		return trim ? trimToEmpty(String.valueOf(object)) : String.valueOf(object);
	}
	
	public static boolean isLetterDigitOrChinese(String str) {
		  String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
		  return str.matches(regex);
	}
	
	public static boolean is_number(String number) {
		if(number==null) return false;
	    return number.matches("[+-]?[1-9]+[0-9]*(\\.[0-9]+)?");    
	}
	
	public static boolean is_alpha(String alpha) {
		if(alpha==null) return false;
	    return alpha.matches("[a-zA-Z]+");    
	}
	
	public static boolean is_chinese(String chineseContent) {
		if(chineseContent==null) return false;
		return chineseContent.matches("[\u4e00-\u9fa5]");
	}
	
	/**
	 * 
	 * @方法名： buildRandom 
	 * @作者：xyd
	 * @日期时间：2015-6-1 上午11:00:13 
	 * @返回类型：int 
	 * @描述：取出一个指定长度大小的随机正整数.
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	
	/**
	 * 
	 * @方法名： isNumber 
	 * @作者：xyd
	 * @日期时间：2017-12-12 下午03:47:52 
	 * @返回类型：boolean 
	 * @描述：是否是数字
	 */
	public static boolean isNumber(String number) {
		if (number == null)
			return false;
		return number.matches("[+-]?[1-9]+[0-9]*(\\.[0-9]+)?");
	}

	public static String sign(Map<String,String> map, String privateKey){
		Collection<String> keyset= map.keySet();
		List<String> keyList= new ArrayList<String>(keyset);
		Collections.sort(keyList);
		StringBuilder sb = new StringBuilder();
		for (String key : keyList){
			sb.append(key).append("=").append(map.get(key)).append("&");
		}
		sb.append("key=").append(privateKey);
		return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
	}
}