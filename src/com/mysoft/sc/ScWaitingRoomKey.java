package com.mysoft.sc;

import com.serotonin.messaging.WaitingRoomKey;
/**
 * 对响应数据帧头分析判断类
 * @author yangfan ej
 *
 */
public class ScWaitingRoomKey implements WaitingRoomKey {
	
	private int ctrlCode;	
	private int addr;
	private short end;
	
	public ScWaitingRoomKey(int ctrlCode, int addr, short end) {
		super();
		this.ctrlCode = ctrlCode;
		this.addr = addr;
		this.end = end;
	}
	public int getCtrlCode() {
		return ctrlCode;
	}
	public void setCtrlCode(int ctrlCode) {
		this.ctrlCode = ctrlCode;
	}
	public int getAddr() {
		return addr;
	}
	public void setAddr(int addr) {
		this.addr = addr;
	}
	public short getEnd() {
		return end;
	}
	public void setEnd(short end) {
		this.end = end;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + addr;
		result = prime * result + ctrlCode;
		result = prime * result + end;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScWaitingRoomKey other = (ScWaitingRoomKey) obj;
		if (addr != other.addr)
			return false;
		if (ctrlCode != other.ctrlCode)
			return false;
		if (end != other.end)
			return false;
		return true;
	}
	

}
