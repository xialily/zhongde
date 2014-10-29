package com.mysoft.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysoft.entities.Controller;
import com.mysoft.jdbc.daos.IBaseDao;

import com.mysoft.services.IControllerService;
@Service("controllerService")
@SuppressWarnings("all")
public class ControllerService extends BaseService<Controller> implements IControllerService{
	@Resource(name="controllerDao")
	/**
	 * @param jkdatadao the jkdatadao to set
	 */
	public void setDao(IBaseDao dao) {
		this.setBaseDAO(dao);
	}
	
}
