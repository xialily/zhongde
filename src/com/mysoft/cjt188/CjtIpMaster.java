package com.mysoft.cjt188;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.serotonin.messaging.OutgoingRequestMessage;
import com.serotonin.messaging.StreamTransport;
import com.serotonin.modbus4j.base.BaseMessageParser;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
/**
 * 
 * 上位机主程序 支持网络通讯
 * @author yangfan ej
 *
 */
public class CjtIpMaster extends CjtMaster {
	private final IpParameters ipParameters;
	private final boolean keepAlive;
	private final boolean keepsocket;
	// Runtime fields.
	private BaseMessageParser cjtMessageParser;
//	private LqRequestHandler lqRequestHandler;
	private Socket socket;
    
 //   public ModbusResponse send(ModbusRequest request){
 //   	;
 //   }
    @Override
	public boolean isInitialized() {
        return initialized;
    }
    @Override
	public void init() throws ModbusInitException{
    	cjtMessageParser=new CjtIpSerialMessageParser(true); 
    	CjtIpSerialMessageResponse lqresponse=new CjtIpSerialMessageResponse(null);
//    	lqRequestHandler=new LqRequestHandler(this,lqresponse);
		try {
			if (keepAlive )
				openConnection();
		} catch (Exception e) {
			throw new ModbusInitException(e);
		}

		initialized = true;
   }
	public CjtIpMaster(IpParameters params, boolean keepAlive, Socket socket) {
		super();
		ipParameters = params;
		this.keepAlive = keepAlive;
		if (socket != null) {
			this.socket = socket;
			this.keepsocket = true;
		} else {
			this.keepsocket = false;
		}
	}
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
		conn.start(transport, cjtMessageParser, null);
		transport.start("cjt645 tcpMaster");
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
	@Override
	synchronized public CjtMessage send(CjtMessage request)
			throws ModbusTransportException {
		try {
			// Check if we need to open the connection.
			if (!keepAlive )
				openConnection();
		} catch (Exception e) {
			closeConnection();
			throw new ModbusTransportException(e);
		}

		// Wrap the modbus request in a ip request.
		OutgoingRequestMessage ipRequest;
		ipRequest=new CjtIpSerialMessageRequest(request);
		// Send the request to get the response.
	        if(conn.DEBUG){
			byte b[]=null;
			b=ipRequest.getMessageData();
			for(byte bb:b)
			System.out.print(Integer.toHexString(bb&0xff)+" ");
					System.out.println();
	        }
		CjtIpSerialMessageResponse ipResponse;
		try {
			ipResponse = (CjtIpSerialMessageResponse) conn.send(ipRequest);
			if (ipResponse == null)
				return null;
		            if(conn.DEBUG){
    		byte b[]=null;
    		b=ipResponse.getMessageData();
    		for(byte bb:b)
    		System.out.print(Integer.toHexString(bb&0xff)+" ");
    				System.out.println();
           }
			return ipResponse.getCjtMessage();
		} catch (Exception e) {
			if (keepAlive) {
				// The connection may have been reset, so try to reopen it and
				// attempt the message again.
				try {
					// System.out.println("Modbus4J: Keep-alive connection may have been reset. Attempting to re-open.");
					if (!keepsocket)
						openConnection();
					ipResponse = (CjtIpSerialMessageResponse) conn.send(ipRequest);
					if (ipResponse == null)
						return null;
					return ipResponse.getCjtMessage();
				} catch (Exception e2) {
					throw new ModbusTransportException(e2);
				}
			}

			throw new ModbusTransportException(e);
		} finally {
			// Check if we should close the connection.
			if (!keepAlive && !keepsocket)
				closeConnection();
		}
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		closeConnection();
	}

}
