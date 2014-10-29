package com.mysoft.cjt188;

import com.serotonin.messaging.WaitingRoomKey;
import com.serotonin.util.queue.ByteQueue;
/**
 * 与设备通讯数据包封装类（支持网络协议部分）
 * @author yangfan ej
 *
 */
public class CjtIpSerialMessage {
    protected CjtMessage cjtMessage;

    public CjtIpSerialMessage(CjtMessage cjtMessage) {
        this.cjtMessage=cjtMessage;
    }

    public byte[] getMessageData() {
        ByteQueue queue = new ByteQueue();

        // Write the particular message.
    	for(int i=0;i<2;i++)
    		queue.push(0xfe);

        cjtMessage.write(queue);
        int sum=CjtUtils.calculateSum(cjtMessage);
    	queue.push(sum);
    	queue.push(0x16);


        // Return the data.
        return queue.popAll();
    }
    
    public WaitingRoomKey getWaitingRoomKey() {
    	int ctrlCode=(cjtMessage.getCtrlCode()&0xff)|0x80;
        return new CjtWaitingRoomKey(ctrlCode,cjtMessage.getAddr(),cjtMessage.getType());
    }

	public CjtMessage getCjtMessage() {
		return cjtMessage;
	}

	public void setCjtMessage(CjtMessage cjtMessage) {
		this.cjtMessage = cjtMessage;
	}


}
