package com.mysoft.jdbc.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mysoft.jdbc.daos.IBaseDao;
/**
 * 
 * dao层抽象类
 * @author yangfan ej
 *
 * @param <T>
 */
@SuppressWarnings("all")
public abstract class BaseDao<T> implements IBaseDao<T> {
	@Resource
	private NamedParameterJdbcTemplate jdbctemplate;

	private String tablename;
	/**
	 * @return the jdbctemplate
	 */
	public NamedParameterJdbcTemplate getJdbctemplate() {
		return jdbctemplate;
	}

	/**
	 * @param jdbctemplate the jdbctemplate to set
	 */
	public void setJdbctemplate(NamedParameterJdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	@Override
	public void add(T t) {
		// TODO Auto-generated method stub
		String sql=this.getInsertSql();
				
		Map<String,Object> params=this.getMapbyT(t);

		
		this.getJdbctemplate().update(sql, params);
		
	}

	@Override
	public void batchDelete(Object[] ids) {
		// TODO Auto-generated method stub
		Map<String,Object>[] param=new HashMap[ids.length];
		for(int i=0;i<ids.length;i++){
			
			param[i].put("id", ids[i]);
		}
		String sql=this.getDeleteSql();
		this.getJdbctemplate().batchUpdate(sql, param);
		
	}

	@Override
	public void callProcedure(String sql, Map params) {
		// TODO Auto-generated method stub
		this.getJdbctemplate().getJdbcOperations().execute(sql);
		
	}

	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		Map param=this.getMapbyT(t);
		String sql=this.getDeleteSql();
		this.getJdbctemplate().update(sql, param);
		
	}

	@Override
	public T findById(Object id) {
		// TODO Auto-generated method stub
		Map <String,Object> param=new HashMap<String, Object>();
		param.put("id", id);
		String sql=this.getSelectSql()+" where id=:id";
		RowMapper rowmapper=this.getRowMapper();
		return(T) this.getJdbctemplate().queryForObject(sql, param, rowmapper);
	}

	@Override
	public long getTotalCount(String exp) {
		// TODO Auto-generated method stub
		String sql=this.getSelectCountSql();
		if(exp!=null)
			sql+=" where "+exp;
		return this.getJdbctemplate().getJdbcOperations().queryForLong(sql);
	}

	@Override
	public List<T> list(String exp, int... startIndexAndCount) {
		// TODO Auto-generated method stub
		RowMapper rowmapper=this.getRowMapper();
		String sql=this.getSelectSql();
		if(exp!=null){
			sql+=" where "+exp;
		}
		//if(startIndexAndCount!=null&&startIndexAndCount.length>1){
		//	sql+=" limit "+startIndexAndCount[0]+","+startIndexAndCount[1];
		//}
		return (List<T>) this.getJdbctemplate().getJdbcOperations().query(sql,rowmapper);
	}

	@Override
	public void update(T t) {
		// TODO Auto-generated method stub
		Map<String,Object> params=this.getMapbyT(t);

		String sql=this.getUpdateSql();
				
		this.getJdbctemplate().update(sql, params);
		
	}

	@Override
	public List<T> lists(String sql, Map params,int... startIndexAndCount) {
		// TODO Auto-generated method stub
		RowMapper rowmapper=this.getRowMapper();
		
		//if(startIndexAndCount!=null&&startIndexAndCount.length>1){
		//	sql+=" limit "+startIndexAndCount[0]+","+startIndexAndCount[1];
		//}
		if(params!=null&&params.size()>0)
			return (List<T>) this.getJdbctemplate().query(sql,params,rowmapper);
		return (List<T>) this.getJdbctemplate().getJdbcOperations().query(sql,rowmapper);
	}


	@Override
	public void setTablename(String tablename) {
		// TODO Auto-generated method stub
		this.tablename=tablename;
	}

	public abstract Map getMapbyT(T t);
	protected abstract RowMapper getRowMapper();
	public abstract String getInsertSql();
	public abstract String getUpdateSql();
	public  String getDeleteSql(){
		String sql="delete from "+tablename +" where id=:id";
		return sql;
	};
	public  String getSelectSql(){
		String sql="select * from "+tablename;
		return sql;
		
	}
	public  String getSelectCountSql(){
		String sql="select count(*) from "+tablename;
		return sql;

	}
	
}
