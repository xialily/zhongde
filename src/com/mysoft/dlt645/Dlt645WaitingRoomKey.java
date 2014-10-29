package com.mysoft.dlt645;

import java.util.Arrays;

import com.serotonin.messaging.WaitingRoomKey;
/**
 * 对响应数据帧头分析判断类
 * @author yangfan ej
 *
 */
public class Dlt645WaitingRoomKey implements WaitingRoomKey {
	
	private int ctrlCode;
	private byte[] addr;
	

	public Dlt645WaitingRoomKey(int ctrlCode, byte[] addr) {
		super();
		this.ctrlCode = ctrlCode;
		this.addr = addr;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(addr);
		result = prime * result + ctrlCode;
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
		Dlt645WaitingRoomKey other = (Dlt645WaitingRoomKey) obj;
		if (!Arrays.equals(addr, other.addr))
			return false;
		if (ctrlCode != other.ctrlCode)
			return false;
		return true;
	}

}
