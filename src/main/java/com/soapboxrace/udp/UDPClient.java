package com.soapboxrace.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.ParameterBO;

@Stateless
public class UDPClient {

	@EJB
	private ParameterBO parameterBO;

	public void sendRaceUdpKey(byte[] key) {
		try {
			String password = parameterBO.getStrParam("UDP_RACE_PASSWORD");
			if (password == null || password.isEmpty()) {
				return;
			}
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(parameterBO.getStrParam("UDP_RACE_IP"));
			byte[] receiveData = new byte[16];
			ByteBuffer bytebuff = ByteBuffer.allocate(64);
			bytebuff.put((byte) 0x99);
			bytebuff.put(password.getBytes());
			bytebuff.put(key);
			byte[] sendData = Arrays.copyOfRange(bytebuff.array(), 0, bytebuff.position());
			// sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, parameterBO.getIntParam("UDP_RACE_PORT"));
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.setSoTimeout(5000);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData()).trim();
			clientSocket.close();
			if (modifiedSentence == null || !modifiedSentence.equals("0")) {
				throw new Exception("UDP SERVER REJECTED PASSWORD OR TIMED OUT");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void sendFreeroamUdpKey(byte[] key) {
		try {
			String password = parameterBO.getStrParam("UDP_FREEROAM_PASSWORD");
			if (password == null || password.isEmpty()) {
				return;
			}
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(parameterBO.getStrParam("UDP_FREEROAM_IP"));
			byte[] receiveData = new byte[16];
			ByteBuffer bytebuff = ByteBuffer.allocate(64);
			bytebuff.put((byte) 0x99);
			bytebuff.put(password.getBytes());
			bytebuff.put(key);
			byte[] sendData = Arrays.copyOfRange(bytebuff.array(), 0, bytebuff.position());
			// sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, parameterBO.getIntParam("UDP_FREEROAM_PORT"));
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.setSoTimeout(5000);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData()).trim();
			clientSocket.close();
			if (modifiedSentence == null || !modifiedSentence.equals("0")) {
				throw new Exception("UDP SERVER REJECTED PASSWORD OR TIMED OUT");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

}
