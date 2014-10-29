package com.mysoft.sc;

import com.serotonin.messaging.IncomingResponseMessage;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.util.queue.ByteQueue;

public class ScIpSerialMessageResponse extends ScIpSerialMessage implements  IncomingResponseMessage {

	public ScIpSerialMessageResponse(ScMessage dltMessage) {
		super(dltMessage);
		// TODO Auto-generated constructor stub
	}
	
	public static ScIpSerialMessageResponse createScIpSerialMessageResponse(ByteQueue queue) throws ModbusTransportException{
		ScMessage dltm=ScMessage.createScMessage(queue);
		ScIpSerialMessageResponse dltr=new ScIpSerialMessageResponse(dltm);
		ScUtils.checkSum(dltr.scMessage, queue);
		
		return dltr;
	}



}
