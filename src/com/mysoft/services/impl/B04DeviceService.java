package com.mysoft.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysoft.entities.B04Device;
import com.mysoft.jdbc.daos.IBaseDao;

import com.mysoft.services.IB04DeviceService;
@Service("b04DeviceService")
@SuppressWarnings("all")
public class B04DeviceService extends BaseService<B04Device> implements IB04DeviceService{
	@Resource(name="b04DeviceDao")
	/**
	 * @param jkdatadao the jkdatadao to set
	 */
	public void setDao(IBaseDao dao) {
		this.setBaseDAO(dao);
	}
	
}
