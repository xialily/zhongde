package com.mysoft.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysoft.entities.D01ElectricityData;
import com.mysoft.jdbc.daos.IBaseDao;

import com.mysoft.services.ID01ElectricityDataService;
@Service("d01ElectricityDataService")
@SuppressWarnings("all")
public class D01ElectricityDataService extends BaseService<D01ElectricityData> implements ID01ElectricityDataService{
	@Resource(name="d01ElectricityDataDao")
	/**
	 * @param jkdatadao the jkdatadao to set
	 */
	public void setDao(IBaseDao dao) {
		this.setBaseDAO(dao);
	}
	
}
