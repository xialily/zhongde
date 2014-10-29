package com.serotonin.modbus4j.ip.tcp;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HeartBeat implements Runnable {
	private Socket socket;
public HeartBeat(Socket socket){
	this.socket=socket;
}
@Override
public void run() {
	// TODO Auto-generated method stub
	sendHeartBeat();
}
public void sendHeartBeat(){
	byte data[]=new byte[1024];
	data[0]=0x00;
	data[1]=0x0a;
	data[2]=0x00;
	data[3]=0x03;
	data[4]=0x00;
	data[5]=0x01;
	data[6]=0x30;
	data[7]=0x30;
	data[8]=0x30;
	data[9]=0x31;
	try {
		OutputStream out=socket.getOutputStream();
		BufferedOutputStream bout=new BufferedOutputStream(out);
		bout.write(data);
		bout.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
public void sendLogin(){
	byte data[]=new byte[1024];
	data[0]=0x00;
	data[1]=0x0a;
	data[2]=0x00;
	data[3]=0x01;
	data[4]=0x00;
	data[5]=0x01;
	data[6]=0x30;
	data[7]=0x30;
	data[8]=0x30;
	data[9]=0x31;
	try {
		OutputStream out=socket.getOutputStream();
		BufferedOutputStream bout=new BufferedOutputStream(out);
		bout.write(data);
		bout.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
