package com.mysoft.dlt645;

import com.serotonin.messaging.IncomingMessage;
import com.serotonin.modbus4j.base.BaseMessageParser;
import com.serotonin.util.queue.ByteQueue;
/**
 * 接受数据帧后，字段分析封装成类（网络协议部分）
 * @author yangfan ej
 *
 */

public class Dlt645IpSerialMessageParser extends BaseMessageParser {

	public Dlt645IpSerialMessageParser(boolean master) {
		super(master);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected IncomingMessage parseMessageImpl(ByteQueue queue)
			throws Exception {
		// TODO Auto-generated method stub
		byte data[]=null;
		//System.out.print("parser");
		if(queue.size()<14){
			return null;
			//return new LqSerialMessageRequest(new LqMessage());
		}else{
			data=queue.peekAll();
			
			for(byte bb:data)
			System.out.print(Integer.toHexString(bb&0xff)+" ");
					System.out.println();
			
			int length=data[12]&0xff;
			
			if(queue.size()<length+14)
				return null;
		}

		queue.pop(3);
		int bsuc=data[11]&0x40;
		if(bsuc==0)
			return Dlt645IpSerialMessageResponse.createDlt645IpSerialMessageResponse(queue);
		else{
			Dlt645IpSerialMessageRequest request=Dlt645IpSerialMessageRequest.createDlt645IpMessageRequest(queue);
			queue.pop(2);
			return request;
		}
	}

}
