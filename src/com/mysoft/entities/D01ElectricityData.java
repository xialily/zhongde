package com.mysoft.entities;

public class D01ElectricityData {
	private int id;
	private int b04id;
	private String monthno;		//年月日
	private String hourtime;	//时分秒
	private double vvalue;		//电压（V）
	private double avalue;		//电流（A）
	private double ypwvalue;	//有功（KW）
	private double npwvalue;	//无功（KW）
	private double kwhvalue;	//电量（kWh）
	private String remark;		//备注
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
	public double getVvalue() {
		return vvalue;
	}
	public void setVvalue(double vvalue) {
		this.vvalue = vvalue;
	}
	public double getAvalue() {
		return avalue;
	}
	public void setAvalue(double avalue) {
		this.avalue = avalue;
	}
	public double getYpwvalue() {
		return ypwvalue;
	}
	public void setYpwvalue(double ypwvalue) {
		this.ypwvalue = ypwvalue;
	}
	public double getNpwvalue() {
		return npwvalue;
	}
	public void setNpwvalue(double npwvalue) {
		this.npwvalue = npwvalue;
	}
	public double getKwhvalue() {
		return kwhvalue;
	}
	public void setKwhvalue(double kwhvalue) {
		this.kwhvalue = kwhvalue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}

