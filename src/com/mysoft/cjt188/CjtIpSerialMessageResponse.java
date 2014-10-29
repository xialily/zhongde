package com.mysoft.cjt188;

import com.serotonin.messaging.IncomingResponseMessage;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.util.queue.ByteQueue;

public class CjtIpSerialMessageResponse extends CjtIpSerialMessage implements  IncomingResponseMessage {

	public CjtIpSerialMessageResponse(CjtMessage cjtMessage) {
		super(cjtMessage);
		// TODO Auto-generated constructor stub
	}
	
	public static CjtIpSerialMessageResponse createCjtIpSerialMessageResponse(ByteQueue queue) throws ModbusTransportException{
		CjtMessage cjtm=CjtMessage.createCjtMessage(queue);
		CjtIpSerialMessageResponse cjtr=new CjtIpSerialMessageResponse(cjtm);
		CjtUtils.checkSum(cjtr.cjtMessage, queue);
		queue.pop();
		return cjtr;
	}



}
