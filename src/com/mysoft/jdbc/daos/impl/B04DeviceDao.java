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


import com.mysoft.entities.B04Device;
import com.mysoft.jdbc.daos.IBaseDao;
/**
 * 电表dao
 * 
 * @author yangfan
 *
 */
@Repository("b04DeviceDao")
@SuppressWarnings("all")
public  class B04DeviceDao extends BaseDao<B04Device>  implements IBaseDao<B04Device> {
	
@Resource
	private NamedParameterJdbcTemplate jdbctemplate;
	
public B04DeviceDao(){
	this.setTablename("b04device");
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
	public Map getMapbyT(B04Device t) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("id", t.getId());
		params.put("equipmentId", t.getEquipmentId());
		params.put("ipAddress", t.getIpAddr());
		params.put("ipport", t.getIpPort());
		params.put("deviceType", t.getDeviceType());
		params.put("equipmentType", t.getEquipmentType());
		params.put("company", t.getCompany());
		params.put("upId", t.getUpId());
		params.put("b02Id", t.getB02Id());
		params.put("b03Id", t.getB03Id());
		params.put("location", t.getLocation());
		params.put("status", t.getStatus());
		params.put("remark", t.getRemark());
		params.put("huganbi", t.getHuganbi());
		params.put("ten", t.getTen());
		return params;
	}

	@Override
	protected RowMapper getRowMapper() {
		// TODO Auto-generated method stub
		return new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				B04Device edata=new B04Device();
				edata.setId(rs.getInt("id"));
				edata.setEquipmentId(rs.getString("equipmentid"));
				edata.setIpAddr(rs.getString("ipaddress"));
				edata.setIpPort(rs.getInt("ipport"));
				edata.setDeviceType(rs.getString("devicetype"));
				edata.setEquipmentType(rs.getString("equipmentType"));
				edata.setCompany(rs.getString("company"));
				edata.setUpId(rs.getInt("upid"));
				edata.setB02Id(rs.getInt("b02id"));
				edata.setB03Id(rs.getInt("b03id"));
				edata.setLocation(rs.getString("location"));
				edata.setStatus(rs.getString("status"));
				edata.setRemark(rs.getString("remark"));
				edata.setHuganbi(rs.getInt("huganbi"));
				edata.setTen(rs.getInt("ten"));
				return edata;
			}
			
		};
	}

	@Override
	public String getInsertSql() {
		// TODO Auto-generated method stub
		String sql="insert into B04Device(id,equipmentid,ipaddress,ipport,devicetype,equipmenttype,company,upid,b02id,b03id,location,status,remark)values(seq_device.nextval,:equipmentId,:ipAddress,:ipPort,:deviceType,:equipmentType,:company,:upId,:b02Id,:b03Id,:location,:status,:remark)";
		return sql;
	}

	@Override
	public String getUpdateSql() {
		// TODO Auto-generated method stub
		String sql="update B04Device set equipmentId=:equipmentId,ipaddress=:ipAddress,ipport=:ipPort,devicetype=:deviceType,equipmentType=:equipmentType,company=:company,upId=:upId,b02Id=:b02Id,b03Id=:b03Id,location=:location,status=:status,remark=:remark where id=:id";
		return sql;
	}

}
