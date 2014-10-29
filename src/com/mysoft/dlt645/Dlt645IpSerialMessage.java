package com.mysoft.dlt645;

import com.serotonin.messaging.WaitingRoomKey;
import com.serotonin.util.queue.ByteQueue;
/**
 * 与设备通讯数据包封装类（支持网络协议部分）
 * @author yangfan ej
 *
 */
public class Dlt645IpSerialMessage {
    protected Dlt645Message dltMessage;

    public Dlt645IpSerialMessage(Dlt645Message dltMessage) {
        this.dltMessage=dltMessage;
    }

    public byte[] getMessageData() {
        ByteQueue queue = new ByteQueue();

        // Write the particular message.
    	for(int i=0;i<4;i++)
    		queue.push(0xfe);

        dltMessage.write(queue);
        int sum=Dlt645Utils.calculateSum(dltMessage);
    	queue.push(sum);
    	queue.push(0x16);


        // Return the data.
        return queue.popAll();
    }
    
    public WaitingRoomKey getWaitingRoomKey() {
    	int ctrlCode=(dltMessage.getCtrlCode()&0xff)|0x80;
        return new Dlt645WaitingRoomKey(ctrlCode,dltMessage.getAddr());
    }

	public Dlt645Message getDltMessage() {
		return dltMessage;
	}

	public void setDltMessage(Dlt645Message dltMessage) {
		this.dltMessage = dltMessage;
	}



}
