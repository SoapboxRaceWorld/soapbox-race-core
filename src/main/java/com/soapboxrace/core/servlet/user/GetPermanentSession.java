package com.soapboxrace.core.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.soapboxrace.jaxb.http.ArrayOfProfileData;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.User;
import com.soapboxrace.jaxb.http.UserInfo;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/User/GetPermanentSession" })
public class GetPermanentSession extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2084506205149117495L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		UserInfo userInfo = new UserInfo();
		userInfo.setPersonas(new ArrayOfProfileData());
		User user = new User();
		user.setSecurityToken("1234567890");
		user.setUserId(69L);
		userInfo.setUser(user);
		String marshal = MarshalXML.marshal(new ObjectFactory().createUserInfo(userInfo));
		response.getOutputStream().write(marshal.getBytes());
	}

}
