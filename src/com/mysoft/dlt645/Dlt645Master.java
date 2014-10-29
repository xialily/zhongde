package com.mysoft.dlt645;

import com.serotonin.messaging.MessageControl;
import com.serotonin.messaging.StreamTransport;
import com.serotonin.modbus4j.Modbus;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
/**
 * 上位机主程序抽象类
 * 
 * @author yangfan ej
 *
 */
public abstract class  Dlt645Master extends Modbus{
    private int timeout = 500;
    private int retries = 2;
   protected MessageControl conn;
   protected boolean initialized;
   protected StreamTransport transport;
   

   abstract public void init() throws ModbusInitException;  
   

   abstract public void destroy();

   abstract public Dlt645Message send(Dlt645Message request) throws ModbusTransportException;
   protected MessageControl getMessageControl() {
       MessageControl conn = new MessageControl();
       conn.setRetries(getRetries());
       conn.setTimeout(getTimeout());
       conn.setExceptionHandler(getExceptionHandler());
       return conn;
   }

   protected void closeMessageControl(MessageControl conn) {
       if (conn != null)
           conn.close();
   }
 
public int getTimeout() {
	return timeout;
}
public void setTimeout(int timeout) {
	this.timeout = timeout;
}
public int getRetries() {
	return retries;
}
public void setRetries(int retries) {
	this.retries = retries;
}
public MessageControl getConn() {
	return conn;
}
public void setConn(MessageControl conn) {
	this.conn = conn;
}
public boolean isInitialized() {
	return initialized;
}
public void setInitialized(boolean initialized) {
	this.initialized = initialized;
}
public StreamTransport getTransport() {
	return transport;
}
public void setTransport(StreamTransport transport) {
	this.transport = transport;
}
	 
}
