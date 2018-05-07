package com.soapboxrace.core.api.util;

import java.time.format.DateTimeFormatter;

import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.jaxb.login.LoginStatusVO;

public class BanUtil {
	public static final DateTimeFormatter banEndFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private BanEntity banEntity;

	public BanUtil(BanEntity banEntity) {
		this.banEntity = banEntity;
	}

	public LoginStatusVO invoke() {
		LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
		LoginStatusVO.Ban ban = new LoginStatusVO.Ban();
		ban.setReason(banEntity.getReason());
		ban.setExpires(banEntity.getEndsAt() == null ? null : banEndFormatter.format(banEntity.getEndsAt()));

		loginStatusVO.setBan(ban);
		return loginStatusVO;
	}

}
