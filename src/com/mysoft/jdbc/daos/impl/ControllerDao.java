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


import com.mysoft.entities.Controller;
import com.mysoft.jdbc.daos.IBaseDao;
/**
 * 电表dao
 * 
 * @author yangfan
 *
 */
@Repository("controllerDao")
@SuppressWarnings("all")
public  class ControllerDao extends BaseDao<Controller>  implements IBaseDao<Controller> {
	
@Resource
	private NamedParameterJdbcTemplate jdbctemplate;
	
public ControllerDao(){
	this.setTablename("controller");
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
	public Map getMapbyT(Controller t) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("id", t.getId());
		params.put("ipaddr", t.getIpaddr());
		params.put("ipport", t.getIpport());
		params.put("type", t.getType());
		params.put("controllername", t.getControllername());
		return params;
	}

	@Override
	protected RowMapper getRowMapper() {
		// TODO Auto-generated method stub
		return new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Controller edata=new Controller();
				edata.setId(rs.getInt("id"));
				edata.setIpaddr(rs.getString("ipaddr"));
				edata.setIpport(rs.getInt("ipport"));
				edata.setType(rs.getInt("type"));
				edata.setControllername(rs.getString("controllername"));
				
				return edata;
			}
			
		};
	}

	@Override
	public String getInsertSql() {
		// TODO Auto-generated method stub
		String sql="insert into Controller(id,ipaddr,ipport,type,controllername)values(seq_controller.nextval,:ipaddr,:ipport,:type,:controllername)";
		return sql;
	}

	@Override
	public String getUpdateSql() {
		// TODO Auto-generated method stub
		String sql="update Controller set ipaddr=:ipaddr,ipport=:ipport,type=:type,controllername=:controllername where id=:id";
		return sql;
	}

}
