package com.mysoft.entities;

public class B04Device {
private int id;
private String equipmentId;			//设备标识（跟数据采集编号一致）
private String ipAddr;
private int ipPort;

/*设备分类，包括如下
1：电表，
2：水表
3：热表，
4：空调，
5：太阳能温度，
6：太阳能风速，
7：太阳能辐照度*/
private String deviceType;
private String equipmentType;		//设备型号
private String company;				//设备生产厂商
private int upId;					//汇总表的id（汇总表，则为空）
private int b02Id;					//关联建筑物表
private int b03Id;					//关联功能码表
private String location;			//设备位置
private String status;				//设备状态（0：正常，1：损坏）
private String remark;				//备注（具体功能细项等）
private int huganbi;				//电表的互感比
private int ten;					//电表的十进制数
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getEquipmentId() {
	return equipmentId;
}
public void setEquipmentId(String equipmentId) {
	this.equipmentId = equipmentId;
}
public String getIpAddr() {
	return ipAddr;
}
public void setIpAddr(String ipAddr) {
	this.ipAddr = ipAddr;
}
public int getIpPort() {
	return ipPort;
}
public void setIpPort(int ipport) {
	this.ipPort = ipport;
}
public String getDeviceType() {
	return deviceType;
}
public void setDeviceType(String deviceType) {
	this.deviceType = deviceType;
}
public String getEquipmentType() {
	return equipmentType;
}
public void setEquipmentType(String equipmentType) {
	this.equipmentType = equipmentType;
}
public String getCompany() {
	return company;
}
public void setCompany(String company) {
	this.company = company;
}
public int getUpId() {
	return upId;
}
public void setUpId(int upId) {
	this.upId = upId;
}
public int getB02Id() {
	return b02Id;
}
public void setB02Id(int b02Id) {
	this.b02Id = b02Id;
}
public int getB03Id() {
	return b03Id;
}
public void setB03Id(int b03Id) {
	this.b03Id = b03Id;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public int getHuganbi() {
	return huganbi;
}
public void setHuganbi(int huganbi) {
	this.huganbi = huganbi;
}
public int getTen() {
	return ten;
}
public void setTen(int ten) {
	this.ten = ten;
}

}
