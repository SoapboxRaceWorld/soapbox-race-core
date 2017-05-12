package com.soapboxrace.jaxb.login;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginStatusVO")
public class LoginStatusVO {

	@XmlElement(name = "UserId")
	private Long userId;
	@XmlElement(name = "LoginToken")
	private String loginToken;
	@XmlElement(name = "Description")
	private String description = "";
	@XmlTransient
	private boolean loginOk;

	public LoginStatusVO(Long userId, String loginToken, boolean loginOk) {
		this.userId = userId;
		this.loginToken = loginToken;
		this.loginOk = loginOk;
	}

	public Long getUserId() {
		return userId;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isLoginOk() {
		return loginOk;
	}

}
