package com.edsonalexandre.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	public static String deodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> decodeURL(String s) {
		List<Integer> list = new ArrayList<>();

		String[] vet = s.split(",");

		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}

		// return Arrays.asList(s.split(",")).stream().map(x ->
		// Integer.parseInt(x)).collect(Collectors.toList());
		return list;
	}
}
