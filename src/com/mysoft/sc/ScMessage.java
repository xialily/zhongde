package com.mysoft.sc;

import com.serotonin.util.queue.ByteQueue;
/**
 * 与设备通讯数据包封装类（核心部分）
 * 
 * @author yangfan ej
 *
 */
public class ScMessage {

	private int ctrlCode;	
	private int addr;
	private int data;
	private short end;



	public ScMessage(){
		
	}


	public ScMessage(int ctrlCode, int addr, int data, short end) {
		super();
		this.ctrlCode = ctrlCode;
		this.addr = addr;
		this.data = data;
		this.end = end;
	}
	
	

	public int getCtrlCode() {
		return ctrlCode;
	}


	public void setCtrlCode(int ctrlCode) {
		this.ctrlCode = ctrlCode;
	}


	public int getAddr() {
		return addr;
	}


	public void setAddr(int addr) {
		this.addr = addr;
	}


	public int getData() {
		return data;
	}


	public void setData(int data) {
		this.data = data;
	}


	public short getEnd() {
		return end;
	}


	public void setEnd(short end) {
		this.end = end;
	}


/**
 * 数据写入队列
 * @param queue
 */
public void write(ByteQueue queue) {
	queue.push(ctrlCode);	
	queue.push(addr);
	queue.push(data);
	queue.pushU2B(end);
}


/**
 * queue 解析
 * @param queue
 * @return
 */
public static ScMessage createScMessage(ByteQueue queue){
	ScMessage scm=new ScMessage();
	
	scm.setCtrlCode(queue.pop());
	scm.setAddr(queue.pop());
	scm.setData(queue.pop());
	scm.setEnd((short)queue.popU2B());

	return scm;
}


}
