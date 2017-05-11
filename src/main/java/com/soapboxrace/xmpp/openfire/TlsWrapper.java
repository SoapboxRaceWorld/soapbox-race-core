package com.soapboxrace.xmpp.openfire;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TlsWrapper {

	private static TrustManager[] getTrustAll() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };
		return trustAllCerts;
	}

	private static SSLContext getSslContext() {
		SSLContext sslctx = null;
		try {
			sslctx = SSLContext.getInstance("TLS");
			sslctx.init(null, getTrustAll(), new SecureRandom());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sslctx;
	}

	public static void wrapXmppTalk(OpenFireTalk xmppTalk) {
		try {
			Socket socket = xmppTalk.getSocket();
			SSLContext sslContext = getSslContext();
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			SSLSocketFactory sf = sslContext.getSocketFactory();
			SSLSocket s = (SSLSocket) (sf.createSocket(socket, remoteAddress.getHostName(), socket.getPort(), true));
			s.setUseClientMode(true);
			s.startHandshake();
			xmppTalk.setSocket(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
