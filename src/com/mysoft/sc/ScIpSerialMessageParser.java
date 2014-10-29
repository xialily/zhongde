package com.mysoft.sc;

import com.serotonin.messaging.IncomingMessage;
import com.serotonin.modbus4j.base.BaseMessageParser;
import com.serotonin.util.queue.ByteQueue;
/**
 * 接受数据帧后，字段分析封装成类（网络协议部分）
 * @author yangfan ej
 *
 */

public class ScIpSerialMessageParser extends BaseMessageParser {

	public ScIpSerialMessageParser(boolean master) {
		super(master);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IncomingMessage parseMessageImpl(ByteQueue queue)
			throws Exception {
		// TODO Auto-generated method stub
		byte data[]=null;
		//System.out.print("parser");
		if(queue.size()<9){
			return null;
			//return new LqSerialMessageRequest(new LqMessage());
		}else{
			data=queue.peekAll();
			
			for(byte bb:data)
			System.out.print(Integer.toHexString(bb&0xff)+" ");
					System.out.println();
			
		}
		queue.pop(2);
	return ScIpSerialMessageResponse.createScIpSerialMessageResponse(queue);
	}

}
