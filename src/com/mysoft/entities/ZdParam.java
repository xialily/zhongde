package com.mysoft.entities;
/**
 * pojo 参数管理
 * @author yangfan ej
 *
 */
public class ZdParam {
private Integer id;
private String paramName;
private String paramValue;
public ZdParam(){
	
}
public ZdParam(Integer id, String paramName, String paramValue) {
	super();
	this.id = id;
	this.paramName = paramName;
	this.paramValue = paramValue;
}
/**
 * @return the id
 */
public Integer getId() {
	return id;
}
/**
 * @param id the id to set
 */
public void setId(Integer id) {
	this.id = id;
}
/**
 * @return the paramName
 */
public String getParamName() {
	return paramName;
}
/**
 * @param paramName the paramName to set
 */
public void setParamName(String paramName) {
	this.paramName = paramName;
}
/**
 * @return the paramValue
 */
public String getParamValue() {
	return paramValue;
}
/**
 * @param paramValue the paramValue to set
 */
public void setParamValue(String paramValue) {
	this.paramValue = paramValue;
}

}
