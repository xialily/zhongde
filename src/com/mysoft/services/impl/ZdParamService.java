package com.mysoft.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mysoft.entities.ZdParam;
import com.mysoft.jdbc.daos.impl.ZdParamDao;

import com.mysoft.services.IZdParamService;

@Service("zdParamService")
@SuppressWarnings("all")
public class ZdParamService extends BaseService<ZdParam> implements IZdParamService{

	@Resource(name="zdParamDao")
	public void setJkParamDao(ZdParamDao jkparamdao){
	this.setBaseDAO(jkparamdao);
}



	@Override
	public ZdParam findByParamName(String paramName) {
		// TODO Auto-generated method stub
		String sql=" param_name='"+paramName+"'";
		List<ZdParam> lparam=this.getBaseDAO().list(sql,null);
		ZdParam jkparam=null;
		if(lparam!=null&&lparam.size()>0)
			jkparam=lparam.get(0);
		return jkparam;
	}
}
