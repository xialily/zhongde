package com.mysoft.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysoft.entities.D02WaterData;
import com.mysoft.jdbc.daos.IBaseDao;

import com.mysoft.services.ID02WaterDataService;
@Service("d02WaterDataService")
@SuppressWarnings("all")
public class D02WaterDataService extends BaseService<D02WaterData> implements ID02WaterDataService{
	@Resource(name="d02WaterDataDao")
	/**
	 * @param jkdatadao the jkdatadao to set
	 */
	public void setDao(IBaseDao dao) {
		this.setBaseDAO(dao);
	}
	
}
