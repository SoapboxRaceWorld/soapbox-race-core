package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import com.soapboxrace.core.bo.interfaces.IGetUserSettingsBO;

@WebServlet(urlPatterns = { "/Engine.svc/getusersettings" })
public class GetUserSettings extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2125697541381049590L;

	@Inject
	private IGetUserSettingsBO getUserSettingsBO;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		Long userId = getUserId(request);
		// Long userId = 1L;
		String token = "";
		String userSettings = getUserSettingsBO.getUserSettings(userId, token);
		response.getOutputStream().write(userSettings.getBytes());
	}

}
