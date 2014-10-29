package com.mysoft.cjt188;

import java.util.Arrays;

import com.serotonin.messaging.WaitingRoomKey;
/**
 * 对响应数据帧头分析判断类
 * @author yangfan ej
 *
 */
public class CjtWaitingRoomKey implements WaitingRoomKey {
	
	private int ctrlCode;
	private byte[] addr;
	private int type;
	

	public CjtWaitingRoomKey(int ctrlCode, byte[] addr,int type) {
		super();
		this.ctrlCode = ctrlCode;
		this.addr = addr;
		this.type=type;
	}

	public int getCtrlCode() {
		return ctrlCode;
	}

	public void setCtrlCode(int ctrlCode) {
		this.ctrlCode = ctrlCode;
	}

	public byte[] getAddr() {
		return addr;
	}
	public void setAddr(byte[] addr) {
		this.addr = addr;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(addr);
		result = prime * result + ctrlCode;
		result = prime * result + type;
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
		CjtWaitingRoomKey other = (CjtWaitingRoomKey) obj;
		if (!Arrays.equals(addr, other.addr))
			return false;
		if (ctrlCode != other.ctrlCode)
			return false;
		if (type != other.type)
			return false;
		return true;
	}


}
