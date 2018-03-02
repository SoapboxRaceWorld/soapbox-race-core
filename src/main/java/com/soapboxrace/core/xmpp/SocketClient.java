package com.soapboxrace.core.xmpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient
{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public SocketClient(String srvAddress, int port)
    {
        int tries = 0;
        while (true)
        {
            if (tries >= 20)
            {
                System.err.println("Failed to connect to XMPP host");
                System.exit(1);
                break;
            }

            try
            {
                tries++;
                System.out.println("Attempting to connect to OpenFire XMPP Host. Attempt #" + String.valueOf(tries));
                socket = new Socket(srvAddress, port);
                out = new PrintWriter(this.socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Connected to OpenFire XMPP Host...");
                break;
            } catch (Exception e)
            {
                System.out.println("Failed, retrying.");
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException j)
                {
                    j.printStackTrace();
                }
            }
        }
    }

    public void send(String command)
    {
        System.out.println("C->S [" + command + "]");
        out.println(command);
        out.flush();
    }

    public String receive()
    {
        String receive = "";
        try
        {
            char[] cbuf = new char[10240];
            in.read(cbuf);
            receive = new String(cbuf);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("S->C [" + receive + "]");
        return receive;
    }

    public Socket getSocket()
    {
        return socket;
    }

}