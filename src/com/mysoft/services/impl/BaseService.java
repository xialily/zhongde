package com.mysoft.services.impl;

import java.util.List;
import java.util.Map;

import com.mysoft.jdbc.daos.IBaseDao;
import com.mysoft.services.IBaseService;
/**
 * service层抽象类
 * @author wanze
 *
 * @param <T>
 */
public class BaseService<T> implements IBaseService<T> {


	private IBaseDao <T> baseDAO;
	
	/**
	 * @return the baseDAO
	 */
	public IBaseDao<T> getBaseDAO() {
		return baseDAO;
	}

	/**
	 * @param baseDAO the baseDAO to set
	 */
	public void setBaseDAO(IBaseDao<T> baseDAO) {
		this.baseDAO = baseDAO;
	}

	@Override
	public void add(T t) {
		// TODO Auto-generated method stub
		baseDAO.add(t);
	}

	@Override
	public void batchDelete(Object[] ids) {
		// TODO Auto-generated method stub
		baseDAO.batchDelete(ids);
	}

	@Override
	public void callProcedure(String hql, Map params) {
		// TODO Auto-generated method stub
		baseDAO.callProcedure(hql, params);
	}

	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		baseDAO.delete(t);
	}

	@Override
	public T findById(Object id) {
		// TODO Auto-generated method stub
		return baseDAO.findById(id);
	}

	@Override
	public long getTotalCount(String hql) {
		// TODO Auto-generated method stub
		return baseDAO.getTotalCount(hql);
	}

	@Override
	public List<T> list(String sql, int... startIndexAndCount) {
		// TODO Auto-generated method stub
		return baseDAO.list(sql, startIndexAndCount);
	}

	@Override
	public void update(T t) {
		// TODO Auto-generated method stub
		baseDAO.update(t);
	}

}
