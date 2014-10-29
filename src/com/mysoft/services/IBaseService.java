package com.mysoft.services;

import java.util.List;
import java.util.Map;
/**
 * service层接口
 * @author yangfan ej
 *
 * @param <T>
 */
public interface IBaseService <T>{
	public T findById( Object id);
	
	public void add(T t);
	public void update(T t);
	public void delete(T t);
	public void batchDelete(Object[]ids);
	
	public List<T> list(String sql,int ...startIndexAndCount);

	public void callProcedure(final String hql,final Map params);
	public long getTotalCount(String hql);

}
