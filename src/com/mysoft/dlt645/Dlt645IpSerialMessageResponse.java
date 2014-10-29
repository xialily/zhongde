package com.mysoft.dlt645;

import com.serotonin.messaging.IncomingResponseMessage;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.util.queue.ByteQueue;

public class Dlt645IpSerialMessageResponse extends Dlt645IpSerialMessage implements  IncomingResponseMessage {

	public Dlt645IpSerialMessageResponse(Dlt645Message dltMessage) {
		super(dltMessage);
		// TODO Auto-generated constructor stub
	}
	
	public static Dlt645IpSerialMessageResponse createDlt645IpSerialMessageResponse(ByteQueue queue) throws ModbusTransportException{
		Dlt645Message dltm=Dlt645Message.createDlt645Message(queue);
		Dlt645IpSerialMessageResponse dltr=new Dlt645IpSerialMessageResponse(dltm);
		Dlt645Utils.checkSum(dltr.dltMessage, queue);
		queue.pop();
		return dltr;
	}



}
