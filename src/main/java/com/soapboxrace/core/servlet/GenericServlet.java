package com.soapboxrace.core.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns = { "/Engine.svc/*" })
public class GenericServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4555876012913874691L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		System.out.println(httpRequest.getRequestURI());
		System.out.println(readInputStream(request));
		String filePath = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length() + 1);
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
		if (inputStream != null) {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			response.getOutputStream().write(buffer.toByteArray());
		}
	}

	protected String getHeader(ServletRequest request, String param) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		return httpRequest.getHeader(param);
	}

	protected Long getUserId(ServletRequest request) {
		return Long.valueOf(getHeader(request, "userId"));
	}

	protected String readInputStream(ServletRequest request) {
		StringBuilder buffer = new StringBuilder();
		try {
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	protected String[] splitUri(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		return httpRequest.getRequestURI().split("/");
	}

}
