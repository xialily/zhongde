package com.mysoft.entities;

public class D03HeatData {
private int id;					//编号
private int b04id;				//关联设备信息表设备编号
private String monthno;			//年月日
private String hourtime;		//时分秒
private double currentvalue;	//瞬时热量（J）
private double totalstream;		//累计热量（J）
private String remark;			//备注
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getB04id() {
	return b04id;
}
public void setB04id(int b04id) {
	this.b04id = b04id;
}
public String getMonthno() {
	return monthno;
}
public void setMonthno(String monthno) {
	this.monthno = monthno;
}
public String getHourtime() {
	return hourtime;
}
public void setHourtime(String hourtime) {
	this.hourtime = hourtime;
}
public double getCurrentvalue() {
	return currentvalue;
}
public void setCurrentvalue(double currentvalue) {
	this.currentvalue = currentvalue;
}
public double getTotalstream() {
	return totalstream;
}
public void setTotalstream(double totalstream) {
	this.totalstream = totalstream;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}

}
