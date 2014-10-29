package com.mysoft.sc;

import com.serotonin.messaging.WaitingRoomKey;
import com.serotonin.util.queue.ByteQueue;
/**
 * 与设备通讯数据包封装类（支持网络协议部分）
 * @author yangfan ej
 *
 */
public class ScIpSerialMessage {
    protected ScMessage scMessage;

    public ScIpSerialMessage(ScMessage scMessage) {
        this.scMessage=scMessage;
    }

    public byte[] getMessageData() {
        ByteQueue queue = new ByteQueue();

        // Write the particular message.
    	
    		queue.push(0xcc);
    		queue.push(0xdd);

        scMessage.write(queue);
        
        int sum=ScUtils.calculateSum(scMessage);
    	queue.pushU2B(sum);
    	


        // Return the data.
        return queue.popAll();
    }
    
    public WaitingRoomKey getWaitingRoomKey() {
    	
        return new ScWaitingRoomKey(scMessage.getCtrlCode(),scMessage.getAddr(),scMessage.getEnd());
    }

	public ScMessage getScMessage() {
		return scMessage;
	}

	public void setScMessage(ScMessage scMessage) {
		this.scMessage = scMessage;
	}



}
