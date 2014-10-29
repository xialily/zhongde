package com.mysoft.devicereader;

import gnu.io.SerialPort;

import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.mysoft.cjt188.CjtControlCode;
import com.mysoft.cjt188.CjtIpMaster;
import com.mysoft.cjt188.CjtMaster;
import com.mysoft.cjt188.CjtMessage;
import com.mysoft.cjt188.CjtSerialMaster;
import com.mysoft.cjt188.CjtUtils;
import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
/**
 * 与远程设备通讯类
 * 
 * @author yangfan ej
 *
 */
public class CjtReader  {
	private CjtMaster master;
	private Socket socket;

	

	public CjtReader(){
		//this.initTcp("192.168.1.101", 4001, null);
		
	}
	public CjtReader(String ipaddr,int ipport,Socket socket){
		this.socket=socket;
		this.initTcp(ipaddr, ipport, socket);

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
	master = new CjtIpMaster(params, true, socket);
	// master.setRetries(4);
	master.setTimeout(5000);
	master.setRetries(2);

	try {
		master.init();
	} catch (ModbusInitException ex) {
		Logger.getLogger(CjtReader.class.getName()).log(Level.SEVERE, null, ex);
	}

}
public void initRtu(String serialport){

SerialParameters params = new SerialParameters();

try {

//设定MODBUS通讯的串行口

params.setCommPortId(serialport);

      //设定成无奇偶校验

params.setParity(SerialPort.PARITY_EVEN);

      //设定成数据位是8位

params.setDataBits(8);

      //设定为1个停止位

params.setStopBits(1);

      //串行口上的波特率

params.setBaudRate(9600);

master = new CjtSerialMaster(params);
master.setTimeout(5000);

master.setRetries(1);

master.init();


} catch (Exception e) {

//TODO: handle exception

e.printStackTrace();

}

}
public void destroy(){
if(socket!=null)
	this.master.getConn().close();
else
	this.master.destroy();

} 
public  double readFlow(String saddr){
		CjtControlCode cc = new CjtControlCode();
		byte[] data = null;
		byte[] data2 = null;
		byte[] addr = getAddr(saddr);
		double result = 999;
		CjtMessage message = new CjtMessage(addr, cc.getReadDataCode(),
				cc.getReadFlowData(), CjtControlCode.TYPE_WATER);

		try {
			CjtMessage response = this.master.send(message);
			data = response.getData();
			if (data == null) {
				return result;
			}
			data2 = Arrays.copyOfRange(data, 3, 7);
			result = CjtUtils.BCDToLong(data2) * 0.01;

		} catch (ModbusTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
}

private static byte[] getAddr(String saddr){
	byte[]addr=new byte[7];
	Arrays.fill(addr,(byte)0);
	addr[0]=(byte)Short.parseShort(saddr.substring(6,8),16);
	addr[1]=(byte)Short.parseShort(saddr.substring(4,6),16);
	addr[2]=(byte)Short.parseShort(saddr.substring(2,4),16);
	addr[3]=(byte)(Short.parseShort(saddr.substring(0,2),16));
	addr[4]=0x00;
	addr[5]=0x33;
	addr[6]=0x78;
	
	return addr;
	
}
public static void main(String args[]){
//	CjtReader dr=new CjtReader("10.0.130.252",4001,null);
	
//	double result=dr.readFlow("04130511");
//		System.out.println("result:"+result);
	byte []addr=CjtReader.getAddr("04130511");
	for(byte b:addr)
	System.out.print(" "+Integer.toHexString(b&0xff));
 }
}