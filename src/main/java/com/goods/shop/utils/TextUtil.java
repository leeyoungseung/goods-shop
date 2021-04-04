package com.goods.shop.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtil {

	private TextUtil() {}
	
	public static TextUtil getInstance() {
		return TextUtilHolder.INSTANCE;
	}
	
	private static class TextUtilHolder {
		private static final TextUtil INSTANCE = new TextUtil();
	}
	
	/**
	 * Null 또는 공백이면 true, 아니면 false를 리턴
	 * @param target
	 * @return
	 */
	public boolean isNullOrEmpty(String target) {
		if (target == null || target.trim().equals("") ||target.isEmpty()) 
			return true;
		else 
			return false;
	}
	
	
	/**
	 * 구분자를 기준으로 입력한 문자열을 자른 문자열의 리스트를 리턴하는 메소드
	 * This method return a List of String that split String entered based on separator.
	 * @param separator
	 * @param targetStr
	 * @return
	 */
	public List<String> makeListFromStr(String separator, String targetStr) {
		if (isNullOrEmpty(separator) || isNullOrEmpty(targetStr)) 
			return null;
		
		List<String> res = new ArrayList<String>(Arrays.asList(targetStr.split(separator)));
		
		if (res != null && 0 < res.size()) 
			return res;
		else 
			return null;
	}
	
	/**
	 * 구분자를 기준으로 입력한 문자열을 자른 문자열의 리스트를 리턴하는 메소드 (.csv 형식 파일을 위한 메소드)
	 * This method when to handle CSV file mainly.
	 * @param targetStr
	 * @return
	 */
	public List<String> makeListFromStr(String targetStr) {
	    return makeListFromStr("," , targetStr);
	}
	
	/**
	 * 구분자를 기준으로 입력한 문자열을 자른 문자열의 배열을 리턴하는 메소드
	 * This method return an Array of String that split String entered based on separator.
	 * @param separator
	 * @param targetStr
	 * @return
	 */
	public String [] makeArrayFromStr(String separator, String targetStr) {
		if (isNullOrEmpty(separator) || isNullOrEmpty(targetStr))
			return null;
		
		String [] res = targetStr.split(separator);
		
		if (res != null && 0 < res.length) 
			return res;
		else 
			return null;
	}
	
	/**
	 * 구분자를 기준으로 입력한 문자열을 자른 문자열의 배열을 리턴하는 메소드 (.csv 형식 파일을 위한 메소드)
	 * This method when to handle CSV file mainly.
	 * @param targetStr
	 * @return
	 */
	public String [] makeArrayFromStr(String targetStr) {
	    return makeArrayFromStr("," , targetStr);
	}
	
	/**
	 * 모든 종류의 공백 (전각, 반각)을 제거한 문자열을 리턴하는 메소드
	 * This method return a String deleted space of all kinds for example (full-width space character,half-width space character) 
	 * @param targetStr
	 * @return
	 */
	public String deleteAllSpace(String targetStr) {
		if (isNullOrEmpty(targetStr))
			return null;
		return targetStr.replaceAll("　", " ").replaceAll("\\s+", "");
	}
	
	/**
	 * 개행문자를 제거한 문자열을 리턴하는 메소드. 개행문자를 직접지정.
	 * This method return a String deleted lineseparator. Set lineseparator up directly.
	 * @param lineSeparator
	 * @param targetStr
	 * @return
	 */
	public String deleteLineSeparator(String lineSeparator, String targetStr) {
		if (isNullOrEmpty(lineSeparator) || isNullOrEmpty(targetStr))
			return null;
		return targetStr.replaceAll(lineSeparator, "");
	}
	
	/**
	 * 개행문자를 제거한 문자열을 리턴하는 메소드, 예약된 개행문자 설정
	 * This method return a String deleted lineseparator. Set reserved lineseparator up.
	 * @param lineSeparator
	 * @param targetStr
	 * @return
	 */
	public String deleteLineSeparator(String targetStr) {
		return targetStr.replaceAll("(\r\n|\r|\n|\n\r)", "");
	}
	
	/**
	 * CSV파일의 레코드와 같이 구분자로 컬럼이 나눠진 문자열을 리턴하는 메소드  
	 * This method return a String split by separator like a recode of CSV file.
	 * @param combinator
	 * @param target
	 * @return
	 */
	public String convertFormatRecode(String combinator, String ...target) {
		if (isNullOrEmpty(combinator) || target == null || target.length == 0)
			return null;
		
		int len = target.length;
		StringBuffer res = new StringBuffer("");
		
		for (int i = 0; i < len; i++) {
			 res.append(target[i]);
			 if (len == 1 || i == (len -1 )) break;
			 res.append(combinator);
		}
		
		return res.toString();
	}
	
	/**
	 * CSV 형식의 레코드 문자열을 리턴하는 메소드.
	 * This method return a String format of CSV
	 * @param target
	 * @return
	 */
	public String convertCsvFormatRecode(String ...target ) {
		return convertFormatRecode(",",target);
	}
	
	
	/**
	 * 문자열을 16진수로 변환해주는 메소드
	 * This method return a String converted from String to Hex 
	 * @param target
	 * @return
	 */
	public String convertStrToHex(String target) {
		if (isNullOrEmpty(target))
				return null;
		String res = "";
		
	    for (int i = 0; i < target.length(); i++) {
	    	res += String.format("%02X ", (int) target.charAt(i));
	    }
	    
		return res;
	}
	
	/**
	 * 16진수를 문자열로 변환해주는 메소드
	 * This method return a String converted from Hex to String 
	 * @param type
	 * @param target
	 * @return
	 */
	public String convertHexToStr(String type, String target) {
		if (isNullOrEmpty(type) || isNullOrEmpty(target))
				return null;
		
		int len = target.length();
		byte[] data = new byte[len/2];
		for (int i = 0; i < len; i += 2) {
			data[i/2] = (byte) ((Character.digit(target.charAt(i), 16) << 4)
					+ Character.digit(target.charAt(i+1), 16));
		}

		String res = null;
		try {
			res = new String(data, type);
			
		} catch (UnsupportedEncodingException e) { e.printStackTrace(); }
	    
		return res;
	}
	
	/**
	 * 문자열을 선택한 방식으로 디코딩해서 리턴하는 메소드
	 * This method return a String decoded by chosen encoding type. 
	 * @param type
	 * @param target
	 * @return
	 */
	public String decodeStr(String type, String target) {
		if (isNullOrEmpty(type) || isNullOrEmpty(target))
				return null;
		String decodeRes = null;
		
		try {
			decodeRes = new String(target.getBytes(type), type);
			
		} catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		
		return decodeRes;
	}
	
	/**
	 * 특정 인코딩 방식으로 인코딩된 문자열을 원하는 인코딩 방식으로 변환해준다
	 * 예시 ) EUC-KR -> UTF-8
	 * This method converts a string of chosen encoding type. 
	 * Have to input parameters original encoding type and chosen encoding type, target string. 
	 * @param oldType
	 * @param newType
	 * @param target
	 * @return
	 */
	public String decodeTypeToType(String oldType, String newType, String target) {
		String originTypeStr = decodeStr(oldType, target);
		return decodeStr(newType, originTypeStr);
	}
	
}
