package com.mysoft.devicereader;

import gnu.io.SerialPort;

import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.mysoft.dlt645.ControlCode;
import com.mysoft.dlt645.Dlt645IpMaster;
import com.mysoft.dlt645.Dlt645Master;
import com.mysoft.dlt645.Dlt645Message;
import com.mysoft.dlt645.Dlt645SerialMaster;
import com.mysoft.dlt645.Dlt645Utils;
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
public class Dlt645Reader  {
	private Dlt645Master master;
	private Socket socket;

	

	public Dlt645Reader(){
	//	this.initRtu(serialport)
	}
	public Dlt645Reader(String ipaddr,int ipport,Socket socket){
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
	master = new Dlt645IpMaster(params, true, socket);
	// master.setRetries(4);
	master.setTimeout(5000);
	master.setRetries(2);

	try {
		master.init();
	} catch (ModbusInitException ex) {
		Logger.getLogger(Dlt645Reader.class.getName()).log(Level.SEVERE, null, ex);
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

master = new Dlt645SerialMaster(params);
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
public  double readEnergy(String saddr){
	double result=0;
	result=readData(saddr,ControlCode.getReadEnergyData(),1);
	return result;
}

public  double readAVoltage(String saddr){
	double result=0;
	result=readData(saddr,ControlCode.getReadAVoltageData(),2);
	return result;
}

public  double readBVoltage(String saddr){
	double result=0;
	result=readData(saddr,ControlCode.getReadBVoltageData(),2);
	return result;
}
public  double readCVoltage(String saddr){
	double result=0;
	result=readData(saddr,ControlCode.getReadCVoltageData(),2);
	return result;
}
public  double readACurrent(String saddr){
	double result=0;
	result=readData(saddr,ControlCode.getReadACurrentData(),3);
	return result;
}
public  double readBCurrent(String saddr){
	double result=0;
	result=readData(saddr,ControlCode.getReadBCurrentData(),3);
	return result;
}
public  double readCCurrent(String saddr){
	double result=0;
	result=readData(saddr,ControlCode.getReadCCurrentData(),3);
	return result;
}
public double readActivePower(String saddr){
	double result=0;
	result=readData(saddr,ControlCode.getReadActivePowerData(),4);
	return result;
	
}
public double readReactivePower(String saddr){
	double result=0;
	result=readData(saddr,ControlCode.getReadReactivePowerData(),4);
	return result;
	
}
public  double readData(String saddr,byte []readData,int type){
	ControlCode cc=new ControlCode();
	byte[]data=null;
	double result=999;
	int addr=Integer.parseInt(saddr, 10);
	Dlt645Message message=new Dlt645Message(addr, cc.getReadDataCode(),  readData);
	try {
		Dlt645Message response=this.master.send(message);
		
		data=response.getData();
		if(data!=null){
			result=procData(data,type);
		}
	} catch (ModbusTransportException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return result;
	
}
public double procData(byte []data,int type ){
	double result=0;
	byte []d;
	switch(type){
	case 1:		//有功总电能(XXXXXX.XX)
		d=new byte[4];
		d[0]=data[4];
		d[1]=data[5];
		d[2]=data[6];
		d[3]=data[7];
		for(int i=0;i<d.length;i++)
			d[i]=(byte)((d[i]&0xff)-0x33);
		result=Dlt645Utils.BCDToLong(d)*0.01;
		
		break;
	case 2:		//A相电压(XXX.X)，
		d=new byte[2];
		d[0]=data[4];
		d[1]=data[5];
		for(int i=0;i<d.length;i++)
			d[i]=(byte)((d[i]&0xff)-0x33);
		result=Dlt645Utils.BCDToLong(d)*0.1;
		break;
	case 3:		//A相电流(XXX.XXX),
		d=new byte[3];
		d[0]=data[4];
		d[1]=data[5];
		d[2]=data[6];
		for(int i=0;i<d.length;i++)
			d[i]=(byte)((d[i]&0xff)-0x33);
		result=Dlt645Utils.BCDToLong(d)*0.001;
		
		break;
	case 4:		//瞬时总有功功率(XX.XXXX)，瞬时总无功功率(XX.XXXX)
		d=new byte[3];
		d[0]=data[4];
		d[1]=data[5];
		d[2]=data[6];
		
		for(int i=0;i<d.length;i++)
			d[i]=(byte)((d[i]&0xff)-0x33);
		result=Dlt645Utils.BCDToLong(d)*0.0001;
		
		break;
	}
	
	
	return result;
}
public static void main(String args[]){
	Dlt645Reader dr=new Dlt645Reader("10.0.130.252",4001,null);
	double result[]=new double[5];
	result[0]=dr.readAVoltage("0f");
	result[1]=dr.readACurrent("0f");
	result[2]=dr.readActivePower("0f");
	result[3]=dr.readReactivePower("0f");
	result[4]=dr.readEnergy("0f");
	
		System.out.println(Arrays.toString(result));
}
}