package com.mysoft.cjt188;

import com.serotonin.util.queue.ByteQueue;
/**
 * 与设备通讯数据包封装类（核心部分）
 * 
 * @author yangfan ej
 *
 */
public class CjtMessage {
private byte addr[];
private int ctrlCode;
private int length;
private byte data[];
private int type;


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

public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}


/**
 * 数据写入队列
 * @param queue
 */
public void write(ByteQueue queue) {
//	for(int i=0;i<4;i++)
//		queue.push(0xfe);
	queue.push(0x68);
	queue.push(type);
	queue.push(addr);
	queue.push(ctrlCode);
	queue.push(length);
	queue.push(data);
//	queue.push(0);
//	queue.peek(0x16);
  
}

public CjtMessage(){
	
}

public CjtMessage(long addr, int ctrlCode,byte[] data, int type) {
	super();
	this.addr = CjtUtils.longToBCD(addr, 7);
	this.ctrlCode = ctrlCode;
	this.length = data.length;
	this.data = data;
	this.type=type;
}


public CjtMessage(byte[] addr, int ctrlCode,  byte[] data, int type) {
	super();
	this.addr = addr;
	this.ctrlCode = ctrlCode;
	this.length = data.length;
	this.data = data;
	this.type = type;
	
	for(byte b:addr){
		System.out.println(" "+Integer.toHexString(b&0xff));
	}
	System.out.print(" " + ctrlCode);
	for(byte b:data){
		System.out.println(" "+Integer.toHexString(b&0xff));
	}
	System.out.print(" " + type);
}
/**
 * queue 解析
 * @param queue
 * @return
 */
public static CjtMessage createCjtMessage(ByteQueue queue){
	CjtMessage dlt=new CjtMessage();
	byte addr[]=new byte[7];
	queue.pop();
	
	dlt.setType(queue.pop());
	
	queue.pop(addr,0, 7);
	dlt.setAddr(addr);
	
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
