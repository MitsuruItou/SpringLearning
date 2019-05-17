package com.company.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommonUtils {

	private static final String forword = "forword:/";

	private static final String redirect = "redirect:/";

	public static String getForWordPath(String targetPath) {
		return forword + targetPath;
	}

	public static String getRedirectPath(String targetPath) {
		return redirect + targetPath;
	}

	public static Map<String, String> getAgeValMap() {
		Map<String, String> retMap = new LinkedHashMap<String, String>();

		for (int i = 1; i <= 100; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(i);
			retMap.put(sb.toString(), sb.toString());
		}

		return retMap;
	}

	public static Map<String, String> getSexValMap() {
		Map<String, String> retMap = new LinkedHashMap<String, String>();
		retMap.put("男性", "男性");
		retMap.put("女性", "女性");
		return retMap;
	}

}
