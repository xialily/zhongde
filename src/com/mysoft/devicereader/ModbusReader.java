package com.mysoft.devicereader;

import gnu.io.SerialPort;

import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.serotonin.io.serial.SerialParameters;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.tcp.TcpMaster;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.msg.ReadInputRegistersRequest;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.serial.rtu.RtuMaster;

public class ModbusReader {
	private ModbusMaster master;
	private Socket socket;

	

	public ModbusReader(){
	//	this.initTcp("192.168.1.101", 4001, null);
		
	}
	public ModbusReader(String ipaddr,int ipport,Socket socket){
		this.socket=socket;
		this.initTcp(ipaddr, ipport, socket);

	}

	/*
	public ModbusReader(Socket socket,LqNetHandler netHandler){
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
	master = new TcpMaster(params, true, socket);
	// master.setRetries(4);
	master.setTimeout(1000);
	master.setRetries(2);

	try {
		master.init();
	} catch (ModbusInitException ex) {
		Logger.getLogger(ModbusReader.class.getName()).log(Level.SEVERE, null, ex);
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

master = new RtuMaster(params);
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
//仪表通讯地址号码
public  int readAddr(String sslaveId){
	int slaveId=Integer.parseInt(sslaveId, 16);
	short []data=readHoldingRegisters(slaveId, 0x1441, 0x01);
	
//	System.out.println(data[0]);
	return data[0];
}
//超声波水表瞬时流量--对
public  double readInstantFlow(String sslaveId){
		double result = 999;
		int slaveId = Integer.parseInt(sslaveId, 16);
		short[] data = readHoldingRegisters(slaveId, 0x00, 0x02);
		if (data == null) {
			return 999;
		}
		int d = ((data[1] & 0xffff) << 16) | (data[0] & 0xffff);
		result = Float.intBitsToFloat(d);
		return result;
}

/**
 * 超声波水表累计流量
 * @param sslaveId
 * @return the value of 
 */
public double readTotalFlow(String sslaveId){
	double result = 999;
	int slaveId=Integer.parseInt(sslaveId, 16);
	short []data=readHoldingRegisters(slaveId, 0x08, 0x04);		//正累积流量
	if(data == null){
		return 999;
	}
	int d=((data[1]&0xffff)<<16)|(data[0]&0xffff);
	int d2=((data[3]&0xffff)<<16)|(data[2]&0xffff);
	data=readHoldingRegisters(slaveId, 1438, 0x01);		//当前累积流量倍乘因子
	if(data == null){
		return result;
	}
//	正累积流量=(N+Nf ) ×10n-3
	result=(d+Float.intBitsToFloat(d2))*Math.pow(10, data[0]-3);
	return result;
}
//热表瞬时流量
public  double readInstantHeat(String sslaveId){
	double result=999;
	int slaveId=Integer.parseInt(sslaveId, 16);
	short []data=readHoldingRegisters(slaveId, 0x02, 0x02);
	if(data == null){
		return 999;
	}
	int d=((data[1]&0xffff)<<16)|(data[0]&0xffff);
	result = Float.intBitsToFloat(d);
	return result;
}
//热表累计热量
public double readTotalHeat(String sslaveId){
	double result=-100;
	int slaveId=Integer.parseInt(sslaveId, 16);
	short []data=readHoldingRegisters(slaveId, 0x1e, 0x04);
	if(data == null){
		return 999;
	}
	long d=((long)(data[1]&0xffff)<<16)|(data[0]&0xffff);
	int d2=((data[3]&0xffff)<<16)|(data[2]&0xffff);
	data=readHoldingRegisters(slaveId, 1439, 0x01);
	result=(d+Float.intBitsToFloat(d2))*Math.pow(10, data[0]-4);
	
	return result;
}
public  short [] readHoldingRegisters( int slaveId, int start, int len) {
    try {
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);

        if (response.isException()){
            System.out.println("Exception response: message=" + response.getExceptionMessage());
            return null;
        }else
            return response.getShortData();
    }
    catch (ModbusTransportException e) {
//    	System.out.println("e.printStackTrace();");
    	//        e.printStackTrace();
    }
    return null;
}

public  short[] readInputRegisters(int slaveId, int start, int len) {
    try {
        ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, start, len);
        ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);

        if (response.isException()){
            System.out.println("Exception response: message=" + response.getExceptionMessage());
            return null;
        }else
            return response.getShortData();
    }
    catch (ModbusTransportException e) {
        e.printStackTrace();
    }
    return null;
}


public static void main(String args[]) throws InterruptedException{
	ModbusReader dr=new ModbusReader("10.0.130.252",4001,null);
	//dr.readInstantFlow("1");
	while(true){
	double result=dr.readTotalFlow("1");
		System.out.println("result:"+result);
		Thread.sleep(1);
	}
	//	dr.destroy();
	
//	byte
//	String str="2385";
//	System.out.print(Arrays.toString(str.getBytes()));
	//byte b[]={0x41,(byte)0x00,(byte)0xc7,0x40};
/*	short s[]={(short)0xc740,0x4100};
	//int bb=((b[0]&0xff)<<24)|((b[1]&0xff)<<16)|((b[2]&0xff)<<8)|b[3];
	int d=(s[1]&0xffff<<16)|s[0];
	System.out.println(Float.intBitsToFloat(d));
*/
}

}
