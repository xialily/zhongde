package com.mysoft.cjt188;

import java.util.Arrays;

public class CjtControlCode {
private int direction;
private int answer;
private int ser=1;
private int functionCode;
public static final int DIR_MASTER=0;
public static final int DIR_SLAVE=1;
public static final int ANS_SUCC=0;
public static final int ANS_ERR=1;
public static final int FOL_NON=0;
public static final int FOL_HAV=1;

public static final int FUN_RESERV=0;
public static final int FUN_READ_DATA=0X1;
public static final int FUN_WRITE_DATA=0X4;
public static final int FUN_READ_ADDR=0X3;
public static final int FUN_WRITE_ADDR=0X15;
public static final int FUN_READ_VER=0X9;
public static final int FUN_WRITE_SYN=0X16;

public static final int TYPE_WATER=0X10;
//public static final int TYPE_WATER=0X19;


public CjtControlCode(){
	this.direction = DIR_MASTER;
	this.answer = ANS_SUCC;
	
};
public CjtControlCode(int direction, int answer,  int functionCode) {
	super();
	this.direction = direction;
	this.answer = answer;

	this.functionCode = functionCode;
}
public Integer getCode(){
	int code=(direction&0x1)<<7|(answer&0x1)<<6|functionCode;
	return code;
}
public int getSer(){
	int s=ser++;
	ser%=256;
	return s;
}
public Integer getReadDataCode(){


	this.functionCode = FUN_READ_DATA;
	
	return this.getCode();
}

public byte[] getReadFlowData(){
	byte []data=new byte[3];
	data[0]=0x1f;
	data[1]=(byte)0x90;
	data[2]=(byte)this.getSer();
	return data;
}


public static void main(String args[]){
	CjtControlCode cc=new CjtControlCode(DIR_MASTER, ANS_SUCC,  FUN_READ_DATA);
	System.out.print(Arrays.toString(cc.getReadFlowData()));
}
}
