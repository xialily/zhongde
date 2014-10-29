package com.mysoft.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysoft.entities.D03HeatData;
import com.mysoft.jdbc.daos.IBaseDao;

import com.mysoft.services.ID03HeatDataService;
@Service("d03HeatDataService")
@SuppressWarnings("all")
public class D03HeatDataService extends BaseService<D03HeatData> implements ID03HeatDataService{
	@Resource(name="d03HeatDataDao")
	/**
	 * @param jkdatadao the jkdatadao to set
	 */
	public void setDao(IBaseDao dao) {
		this.setBaseDAO(dao);
	}
	
}
