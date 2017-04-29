package com.soapboxrace.core.api;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;

import com.soapboxrace.jaxb.http.FraudConfig;

@Path("/Security")
public class Security {

	@Path("/fraudConfig")
	public FraudConfig fraudConfig(@HeaderParam("userId") Long userId) {
		FraudConfig fraudConfig = new FraudConfig();
		fraudConfig.setEnabledBitField(12);
		fraudConfig.setGameFileFreq(1000000);
		fraudConfig.setModuleFreq(360000);
		fraudConfig.setStartUpFreq(1000000);
		fraudConfig.setUserID(userId);
		return fraudConfig;
	}
}
