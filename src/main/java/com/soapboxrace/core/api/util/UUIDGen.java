package com.soapboxrace.core.api.util;

import java.util.UUID;

public class UUIDGen {

	public static String getRandomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static byte[] getRandomUUIDBytes() {
		return hexStringToByteArray(getRandomUUID());
	}

	private static byte[] hexStringToByteArray(String s) {
		s = s.replace(":", "");
		s = s.replace("-", "");
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

}
