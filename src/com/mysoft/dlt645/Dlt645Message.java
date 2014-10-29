package com.mysoft.dlt645;

import com.serotonin.util.queue.ByteQueue;
/**
 * 与设备通讯数据包封装类（核心部分）
 * 
 * @author yangfan ej
 *
 */
public class Dlt645Message {
private byte addr[];
private int ctrlCode;
private int length;
private byte data[];


public byte[] getAddr() {
	return addr;
}
public void setAddr(byte[] addr) {
	this.addr = addr;
}
public int getCtrlCode() {
	return ctrlCode;
}
public void setCtrlCode(int ctrlCode) {
	this.ctrlCode = ctrlCode;
}
public int getLength() {
	return length;
}
public void setLength(int length) {
	this.length = length;
}
public byte[] getData() {
	return data;
}
public void setData(byte[] data) {
	this.data = data;
}
/**
 * 数据写入队列
 * @param queue
 */
public void write(ByteQueue queue) {
	//for(int i=0;i<4;i++)
	//	queue.push(0xfe);
	queue.push(0x68);
	queue.push(addr);
	queue.push(0x68);
	queue.push(ctrlCode);
	queue.push(length);
	queue.push(data);
	//queue.push(0);
	//queue.peek(0x16);
  
}

public Dlt645Message(){
	
}

public Dlt645Message(int addr, int ctrlCode,byte[] data) {
	super();
	this.addr = Dlt645Utils.longToBCD(addr, 6);		//地址域A0-A5
	this.ctrlCode = ctrlCode;	//控制码11H
	this.length = data.length;	//数据域长度：4
	this.data = data;	//数据域
}

public Dlt645Message(byte[] addr, int ctrlCode, int length, byte[] data) {
	super();
	this.addr = addr;
	this.ctrlCode = ctrlCode;
	this.length = length;
	this.data = data;
}
/**
 * queue 解析
 * @param queue
 * @return
 */
public static Dlt645Message createDlt645Message(ByteQueue queue){
	Dlt645Message dlt=new Dlt645Message();
	byte addr[]=new byte[6];
	queue.pop();
	queue.pop(addr,0, 6);
	dlt.setAddr(addr);
	queue.pop();
	dlt.setCtrlCode(queue.pop()&0xff);
	int length=queue.pop()&0xff;
	
	dlt.setLength(length);
	if(length>0){
	byte data[]=new byte[length];
	queue.pop(data,0,length);
	dlt.setData(data);
	}
	return dlt;
}


}
