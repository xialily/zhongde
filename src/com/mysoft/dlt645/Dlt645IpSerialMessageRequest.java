package com.mysoft.dlt645;

import com.serotonin.messaging.OutgoingRequestMessage;
import com.serotonin.util.queue.ByteQueue;
/**
 * 生成请求数据帧类（网络协议部分）
 * @author yangfan ej
 *
 */
public class Dlt645IpSerialMessageRequest extends Dlt645IpSerialMessage implements OutgoingRequestMessage,Dlt645IncomingRequestMessage{

	public static Dlt645IpSerialMessageRequest createDlt645IpMessageRequest(ByteQueue queue){
		Dlt645Message lqm=Dlt645Message.createDlt645Message(queue);
		Dlt645IpSerialMessageRequest lqr=new Dlt645IpSerialMessageRequest(lqm);
		return lqr;
	}
	public Dlt645IpSerialMessageRequest(Dlt645Message lqMessage) {
		super(lqMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean expectsResponse() {
		// TODO Auto-generated method stub
		return true;
	}

}
