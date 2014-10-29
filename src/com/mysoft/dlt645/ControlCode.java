package com.mysoft.dlt645;

import java.util.Arrays;

public class ControlCode {
private int direction;
private int answer;
private int follow;
private int functionCode;
public static final int DIR_MASTER=0;
public static final int DIR_SLAVE=1;
public static final int ANS_SUCC=0;
public static final int ANS_ERR=1;
public static final int FOL_NON=0;
public static final int FOL_HAV=1;

public static final int FUN_RESERV=0;
public static final int FUN_TIME=0X8;
public static final int FUN_READ_DATA=0X11;
public static final int FUN_READ_FOLLOW=0X12;
public static final int FUN_READ_ADDR=0X13;
public static final int FUN_WRITE_DATA=0X14;
public static final int FUN_WRITE_ADDR=0X15;
public static final int FUN_FREEZE_COMMAND=0X16;
public static final int FUN_UPDATE_SPEED=0X17;
public static final int FUN_UPDATE_PASS=0X18;
public static final int FUN_CLEAR_MAX=0X19;
public static final int FUN_CLEAR_METER=0X1A;
public static final int FUN_CLEAR_EVENT=0X1B;


public ControlCode(){
	this.direction = DIR_MASTER;
	this.answer = ANS_SUCC;
	this.follow = FOL_NON;
};
public ControlCode(int direction, int answer, int follow, int functionCode) {
	super();
	this.direction = direction;
	this.answer = answer;
	this.follow = follow;
	this.functionCode = functionCode;
}
public Integer getCode(){
	int code=(direction&0x1)<<7|(answer&0x1)<<6|(follow&0x1)<<5|functionCode;
	return code;
}

public Integer getReadDataCode(){


	this.functionCode = FUN_READ_DATA;
	
	return this.getCode();
}
//(当前)组合有功总电能
public static byte[] getReadEnergyData(){
	byte []data=new byte[4];
	Arrays.fill(data, (byte)0x33);
	data[0]+=0;
	data[1]+=0;
	data[2]+=1;
	data[3]+=0;
	return data;
}
//A相电压
public static byte[] getReadAVoltageData(){
	byte []data=new byte[4];
	Arrays.fill(data, (byte)0x33);
	data[0]+=0;
	data[1]+=1;
	data[2]+=1;
	data[3]+=2;
	
	return data;
}
//B相电压
public static byte[] getReadBVoltageData(){
	byte []data=new byte[4];
	Arrays.fill(data, (byte)0x33);
	
	data[0]+=0;
	data[1]+=2;
	data[2]+=1;
	data[3]+=2;
	
	return data;
}
//C相电压
public static byte[] getReadCVoltageData(){
	byte []data=new byte[4];
	Arrays.fill(data, (byte)0x33);
	data[0]+=0;
	data[1]+=3;
	data[2]+=1;
	data[3]+=2;
	
	return data;
}
//A相电流
public static byte[] getReadACurrentData(){
	byte []data=new byte[4];
	Arrays.fill(data, (byte)0x33);
	data[0]+=0;
	data[1]+=1;
	data[2]+=2;
	data[3]+=2;
	
	return data;
}
//B相电流
public static byte[] getReadBCurrentData(){
	byte []data=new byte[4];
	Arrays.fill(data, (byte)0x33);
	data[0]+=0;
	data[1]+=2;
	data[2]+=2;
	data[3]+=2;
	
	return data;
}
//C相电流
public static byte[] getReadCCurrentData(){
	byte []data=new byte[4];
	Arrays.fill(data, (byte)0x33);
	data[0]+=0;
	data[1]+=3;
	data[2]+=2;
	data[3]+=2;
	
	return data;
}
//瞬时总有功率功率
public static byte[] getReadActivePowerData(){
	byte []data=new byte[4];
	Arrays.fill(data, (byte)0x33);
	data[0]+=0;
	data[1]+=0;
	data[2]+=3;
	data[3]+=2;
	
	return data;
}
//瞬时总无功功率
public static byte[] getReadReactivePowerData(){
	byte []data=new byte[4];
	Arrays.fill(data, (byte)0x33);
	data[0]+=0;
	data[1]+=0;
	data[2]+=4;
	data[3]+=2;
	
	return data;
}


public static void main(String args[]){
	ControlCode cc=new ControlCode(DIR_SLAVE, ANS_SUCC, FOL_NON, FUN_READ_DATA);
	System.out.print(Integer.toHexString(cc.getCode()));
}
}
