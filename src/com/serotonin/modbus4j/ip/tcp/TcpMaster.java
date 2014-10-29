/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.serotonin.modbus4j.ip.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.serotonin.messaging.MessageControl;
import com.serotonin.messaging.OutgoingRequestMessage;
import com.serotonin.messaging.StreamTransport;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.base.BaseMessageParser;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpMessageResponse;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.encap.EncapMessageParser;
import com.serotonin.modbus4j.ip.encap.EncapMessageRequest;
import com.serotonin.modbus4j.ip.xa.XaMessageParser;
import com.serotonin.modbus4j.ip.xa.XaMessageRequest;
import com.serotonin.modbus4j.msg.ModbusRequest;
import com.serotonin.modbus4j.msg.ModbusResponse;

public class TcpMaster extends ModbusMaster {
	// Configuration fields.
	private short nextTransactionId = 0;
	private final IpParameters ipParameters;
	private final boolean keepAlive;
	private final boolean keepsocket;

	// Runtime fields.
	private BaseMessageParser ipMessageParser;
	private Socket socket;
	private StreamTransport transport;
	private MessageControl conn;


	public TcpMaster(IpParameters params, boolean keepAlive, Socket socket) {
		ipParameters = params;
		this.keepAlive = keepAlive;
		if (socket != null) {
			this.socket = socket;
			this.keepsocket = true;
		} else {
			this.keepsocket = false;
		}
	}

	protected short getNextTransactionId() {
		return nextTransactionId++;
	}

	@Override
	synchronized public void init() throws ModbusInitException {
		if (ipParameters.isEncapsulated())
			ipMessageParser = new EncapMessageParser(true);
		else
			ipMessageParser = new XaMessageParser(true);

		try {
			if (keepAlive )
				openConnection();
		} catch (Exception e) {
			throw new ModbusInitException(e);
		}

		initialized = true;
	}

	@Override
	synchronized public void destroy() {
		if(this.keepsocket==true)
			this.conn.close();
		else
		closeConnection();
	}

	@Override
	synchronized public ModbusResponse send(ModbusRequest request)
			throws ModbusTransportException {
		try {
			// Check if we need to open the connection.
			if (!keepAlive )
				openConnection();
		} catch (Exception e) {
			closeConnection();
			throw new ModbusTransportException(e, request.getSlaveId());
		}

		// Wrap the modbus request in a ip request.
		OutgoingRequestMessage ipRequest;
		if (ipParameters.isEncapsulated())
			ipRequest = new EncapMessageRequest(request);
		else
			ipRequest = new XaMessageRequest(request, getNextTransactionId());

		// Send the request to get the response.
		IpMessageResponse ipResponse;
		if(conn.DEBUG){
			System.out.print("msend:");
			byte []dd=ipRequest.getMessageData();
			for(byte d:dd){
//			System.out.print(Integer.toHexString(d&0xff)+" ");
			}
		}
		try {
			byte []dd=ipRequest.getMessageData();
			System.out.println("===========指令开始===========");
			for(byte d:dd){
				System.out.print(Integer.toHexString(d&0xff)+" ");
			}
			System.out.println();	
			System.out.println("===========指令完成===========");
			ipResponse = (IpMessageResponse) conn.send(ipRequest);
			
			if (ipResponse == null)
				return null;
			return ipResponse.getModbusResponse();
		} catch (Exception e) {
			if (keepAlive) {
				// The connection may have been reset, so try to reopen it and
				// attempt the message again.
				try {
					// System.out.println("Modbus4J: Keep-alive connection may have been reset. Attempting to re-open.");
					if (!keepsocket)
						openConnection();
					ipResponse = (IpMessageResponse) conn.send(ipRequest);
					if (ipResponse == null)
						return null;
					
					return ipResponse.getModbusResponse();
				} catch (Exception e2) {
					throw new ModbusTransportException(e2, request.getSlaveId());
				}
			}

			throw new ModbusTransportException(e, request.getSlaveId());
		} finally {
			// Check if we should close the connection.
			if (!keepAlive && !keepsocket)
				closeConnection();
		}
	}

	//
	// /
	// / Private methods
	// /
	//
	private void openConnection() throws IOException {
		// Make sure any existing connection is closed.
		if (!keepsocket) {
			closeConnection();

			// Try 'retries' times to get the socket open.
			int retries = getRetries();
			while (true) {
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress(
							ipParameters.getHost(), ipParameters.getPort()),
							getTimeout());
					break;
				} catch (IOException e) {
					closeConnection();

					if (retries <= 0)
						throw e;
					// System.out.println("Modbus4J: Open connection failed, trying again.");
					retries--;
				}
			}
		}
		transport = new StreamTransport(socket.getInputStream(),
				socket.getOutputStream());
		conn = getMessageControl();
		conn.DEBUG=false;
		conn.start(transport, ipMessageParser, null);
		transport.start("Modbus4J TcpMaster");
	}

	private void closeConnection() {
		closeMessageControl(conn);
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			getExceptionHandler().receivedException(e);
		}

		conn = null;
		socket = null;
	}
}
