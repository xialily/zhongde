package com.mysoft.sc;

import gnu.io.SerialPort;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;
/**
 * 与远程设备通讯类
 * 
 * @author yangfan ej
 *
 */
public class ScReader  {
	private ScMaster master;
	private Socket socket;

	

	public ScReader(){
		this.initTcp("192.168.0.155", 4001, null);
		
	}
	/*
	public Dlt645Reader(Socket socket,LqNetHandler netHandler){
		this.socket=socket;
		if(netHandler!=null)
		netHandler.addLqReader(this);
		if(socket!=null)
		initTcp(socket.getInetAddress().toString(), socket.getPort(), socket);
		else{
		initRtu("COM1");
		addr="0001";
		}
	}
	*/
public void initTcp(String ipaddr, int port, Socket socket) {

	IpParameters params = new IpParameters();
	params.setHost(ipaddr);
	params.setPort(port);
	params.setEncapsulated(true);
	master = new ScIpMaster(params, true, socket);
	// master.setRetries(4);
	master.setTimeout(5000);
	master.setRetries(2);

	try {
		master.init();
	} catch (ModbusInitException ex) {
		Logger.getLogger(ScReader.class.getName()).log(Level.SEVERE, null, ex);
	}

}
public void initRtu(String serialport){

SerialParameters params = new SerialParameters();

try {

//设定MODBUS通讯的串行口

params.setCommPortId(serialport);

      //设定成无奇偶校验

params.setParity(SerialPort.PARITY_NONE);

      //设定成数据位是8位

params.setDataBits(8);

      //设定为1个停止位

params.setStopBits(1);

      //串行口上的波特率

params.setBaudRate(9600);

master = new ScSerialMaster(params);
master.setTimeout(5000);

master.setRetries(1);

master.init();


} catch (Exception e) {

//TODO: handle exception

e.printStackTrace();

}

}
public void destroy(){

this.master.destroy();

} 


public static void main(String args[]){
	ScReader dr=new ScReader();
}
}