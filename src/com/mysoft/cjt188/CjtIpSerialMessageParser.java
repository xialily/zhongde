package com.mysoft.cjt188;

import com.serotonin.messaging.IncomingMessage;
import com.serotonin.modbus4j.base.BaseMessageParser;
import com.serotonin.util.queue.ByteQueue;
/**
 * 接受数据帧后，字段分析封装成类（网络协议部分）
 * @author yangfan ej
 *
 */

public class CjtIpSerialMessageParser extends BaseMessageParser {

	public CjtIpSerialMessageParser(boolean master) {
		super(master);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IncomingMessage parseMessageImpl(ByteQueue queue)
			throws Exception {
		// TODO Auto-generated method stub
		byte data[]=null;
		//System.out.print("parser");
		if(queue.size()<13){
			return null;
			//return new LqSerialMessageRequest(new LqMessage());
		}else{
			data=queue.peekAll();
			
			for(byte bb:data)
			System.out.print(Integer.toHexString(bb&0xff)+" ");
					System.out.println();
			
			int length=data[12]&0xff;
			
			if(queue.size()<length+13)
				return null;
		}

		queue.pop(2);
		int bsuc=data[11]&0x40;
		if(bsuc==0)
			return CjtIpSerialMessageResponse.createCjtIpSerialMessageResponse(queue);
		else{
			CjtIpSerialMessageRequest request=CjtIpSerialMessageRequest.createCjtIpMessageRequest(queue);
			queue.pop(2);
			return request;
		}
	}

}
