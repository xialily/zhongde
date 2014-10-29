package com.mysoft.jdbc.daos;

import java.util.List;
import java.util.Map;
/**
 * 
 * dao层接口
 * @author yangfan ej
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	
	public void setTablename(String tablename);
	
	public T findById( Object id);
	
	public void add(T t);
	public void update(T t);
	public void delete(T t);
	public void batchDelete(Object[]ids);
	
	public List<T> list(String exp,int ...startIndexAndCount);
	public List<T> lists(String sql,Map params,int ...startIndexAndCount);

	public void callProcedure(final String exp,final Map params);
	public long getTotalCount(String exp);

}
