/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.udp;

import com.soapboxrace.core.bo.ParameterBO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

@Stateless
public class UDPClient {

    @EJB
    private ParameterBO parameterBO;

    public void sendRaceUdpKey(byte[] key) {
        sendUdpKey(parameterBO.getStrParam("UDP_RACE_IP"), parameterBO.getIntParam("UDP_RACE_PORT"),
                parameterBO.getStrParam("UDP_RACE_PASSWORD"), key);
    }

    public void sendFreeroamUdpKey(byte[] key) {
        sendUdpKey(parameterBO.getStrParam("UDP_FREEROAM_IP"), parameterBO.getIntParam("UDP_FREEROAM_PORT"),
                parameterBO.getStrParam("UDP_FREEROAM_PASSWORD"), key);
    }


    private void sendUdpKey(String address, Integer port, String password, byte[] key) {
        try {
            if (password == null || password.isEmpty()) {
                return;
            }
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(address);
            byte[] receiveData = new byte[16];
            ByteBuffer bytebuff = ByteBuffer.allocate(64);
            bytebuff.put((byte) 0x99);
            bytebuff.put(password.getBytes());
            bytebuff.put(key);
            byte[] sendData = Arrays.copyOfRange(bytebuff.array(), 0, bytebuff.position());
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.setSoTimeout(5000);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData()).trim();
            clientSocket.close();
            if (!modifiedSentence.equals("0")) {
                throw new Exception("UDP SERVER REJECTED PASSWORD OR TIMED OUT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
