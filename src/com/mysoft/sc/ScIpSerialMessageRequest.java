package com.mysoft.sc;

import com.serotonin.messaging.OutgoingRequestMessage;
import com.serotonin.util.queue.ByteQueue;
/**
 * 生成请求数据帧类（网络协议部分）
 * @author yangfan ej
 *
 */
public class ScIpSerialMessageRequest extends ScIpSerialMessage implements OutgoingRequestMessage,ScIncomingRequestMessage{

	public static ScIpSerialMessageRequest createScIpMessageRequest(ByteQueue queue){
		ScMessage lqm=ScMessage.createScMessage(queue);
		ScIpSerialMessageRequest lqr=new ScIpSerialMessageRequest(lqm);
		return lqr;
	}
	public ScIpSerialMessageRequest(ScMessage lqMessage) {
		super(lqMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean expectsResponse() {
		// TODO Auto-generated method stub
		return true;
	}

}
