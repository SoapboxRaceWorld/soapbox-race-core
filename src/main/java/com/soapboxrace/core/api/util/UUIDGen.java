package com.soapboxrace.core.api.util;

import java.util.UUID;

public class UUIDGen {

	public static String getRandomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

}
