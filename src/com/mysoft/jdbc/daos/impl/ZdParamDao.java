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

import com.mysoft.entities.*;
import com.mysoft.jdbc.daos.IBaseDao;
/**
 * 参数管理
 * 
 * @author yangfan ej
 *
 */
@Repository("zdParamDao")
@SuppressWarnings("all")
public class ZdParamDao extends BaseDao<ZdParam> implements IBaseDao<ZdParam> {
	@Resource
	private NamedParameterJdbcTemplate jdbctemplate;

	public ZdParamDao(){
		this.setTablename("zdparam");
	}
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
	public Map getMapbyT(ZdParam t) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("id", t.getId());

		params.put("param_name", t.getParamName());
		params.put("param_value", t.getParamValue());
		return params;
	}

	@Override
	protected RowMapper getRowMapper() {
		// TODO Auto-generated method stub
		return new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				ZdParam jkparam=new ZdParam();
				jkparam.setId(rs.getInt("id"));
				jkparam.setParamName(rs.getString("param_name"));
				jkparam.setParamValue(rs.getString("param_value"));
				
				
				return jkparam;
			}
			
		};
	}

	@Override
	public String getInsertSql() {
		// TODO Auto-generated method stub
		String sql="insert into zdparam(id,param_name,param_value" +
				")values(seq_param.nextval,:param_name,:param_value)";
		return sql;
	}

	@Override
	public String getUpdateSql() {
		// TODO Auto-generated method stub
		String sql="update zdparam set " +
				"param_name=:param_name,"+
				"param_value=:param_value " +
				" where id=:id";
		return sql;
	}

}
