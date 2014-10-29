package com.mysoft.dlt645;

import java.util.Arrays;

import com.serotonin.modbus4j.base.ModbusUtils;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.util.queue.ByteQueue;
/**
 * 工具类
 * @author yangfan ej
 *
 */
public class Dlt645Utils extends ModbusUtils{
public static int calculateSum(Dlt645Message dltMessage){
	ByteQueue queue=new ByteQueue();
	dltMessage.write(queue);
	int sum=0;
	while(queue.size()>0){
		sum+=ModbusUtils.popUnsignedByte(queue);
	}
	sum=sum&0xff;
	return sum;
}
public static void checkSum(Dlt645Message dltMessage, ByteQueue queue) throws ModbusTransportException{
    int calcCrc = calculateSum(dltMessage);
    
    int givenCrc = ModbusUtils.popUnsignedByte(queue);
    
    if (calcCrc != givenCrc)
        throw new ModbusTransportException("sum mismatch: given=" + givenCrc + ", calc=" + calcCrc,
                0);
	
}
/**
 * 模拟通道数据格式转换
 * @param data
 * @param index
 * @return
 */
public static double getSimulate(byte data[],int index){
	int first=(0xff&data[index+1])|data[index]<<8;
	int second=0xff&data[index+2];
	//String str=first+"."+second;
	
	return first*Math.pow(10, -second);
}
public static byte[] time2Byte(long time){
	byte[]body=new byte[4];
	time=time/1000;
	body[0]=(byte)(time>>24);
	body[1]=(byte)(time>>16);
	body[2]=(byte)(time>>8);
	body[3]=(byte)(time&0xff);
	return body;
}
public static long byte2Time(byte []data){
	long time=0;
	time=(0xff&data[0])<<24|(0xff&data[1])<<16|(0xff&data[2])<<8|(0xff&data[3]);
	
	return time*1000;
}
public static byte[]longToBCD(long data,int length ){
	byte result[]=new byte[length];
	Arrays.fill(result, (byte)0);
	String str=String.valueOf(data);
	int j=0;
	for(int i=str.length()-1;i>=0;i-=2){
		String sstr1=str.substring(i,i+1);
		String sstr2="0";
		if(i-1>=0)
			sstr2=str.substring(i-1,i);
		int d=Integer.valueOf(sstr2)<<4|Integer.valueOf(sstr1);
		result[j++]=(byte)d;
		
	}
	
	return result;
}
public static long BCDToLong(byte[]data){
	long result=0;
	for(int i=data.length-1;i>=0;i--){
		int d=((data[i]&0xff)>>4)*10+(data[i]&0xf);
		result+=d*Math.pow(100, i);
	}
	return result;
}
public static void main(String arg[]){
/*	Long time= new Long(1281986905);
	System.out.println(time);
	byte data[]=time2Byte(time);
	long t=byte2Time(data);
	byte[]b=new byte[5];
	b[0]=22;
	System.arraycopy(data, 0, b,1, 4);
	System.out.print(Arrays.toString(b));
*/
	byte data[]={0x78,0x56,0x34,0x12};
	long a=Dlt645Utils.BCDToLong(data);
	System.out.print(a);
}

}
