package com.mysoft.cjt188;

import com.serotonin.messaging.OutgoingRequestMessage;
import com.serotonin.util.queue.ByteQueue;
/**
 * 生成请求数据帧类（网络协议部分）
 * @author yangfan ej
 *
 */
public class CjtIpSerialMessageRequest extends CjtIpSerialMessage implements OutgoingRequestMessage,CjtIncomingRequestMessage{

	public static CjtIpSerialMessageRequest createCjtIpMessageRequest(ByteQueue queue){
		CjtMessage cjtm=CjtMessage.createCjtMessage(queue);
		CjtIpSerialMessageRequest cjtr=new CjtIpSerialMessageRequest(cjtm);
		return cjtr;
	}
	public CjtIpSerialMessageRequest(CjtMessage cjtMessage) {
		super(cjtMessage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean expectsResponse() {
		// TODO Auto-generated method stub
		return true;
	}

}
