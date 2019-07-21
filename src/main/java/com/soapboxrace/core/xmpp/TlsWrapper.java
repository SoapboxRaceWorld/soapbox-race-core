package com.soapboxrace.core.xmpp;

import javax.net.ssl.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class TlsWrapper {
    private static TrustManager[] getTrustAll() {
        return new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
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

    public static void wrapXmppTalk(IOpenFireTalk xmppTalk) {
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