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


import com.mysoft.entities.D01ElectricityData;
import com.mysoft.jdbc.daos.IBaseDao;
/**
 * 电表dao
 * 
 * @author yangfan
 *
 */
@Repository("d01ElectricityDataDao")
@SuppressWarnings("all")
public  class D01ElectricityDataDao extends BaseDao<D01ElectricityData>  implements IBaseDao<D01ElectricityData> {
	
@Resource
	private NamedParameterJdbcTemplate jdbctemplate;
	
public D01ElectricityDataDao(){
	this.setTablename("D01ElectricityData");
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
	public Map getMapbyT(D01ElectricityData t) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("id", t.getId());
		params.put("b04id", t.getB04id());
		params.put("monthno", t.getMonthno());
		params.put("hourtime", t.getHourtime());
		params.put("vvalue", t.getVvalue());
		params.put("avalue", t.getAvalue());
		params.put("ypwvalue", t.getYpwvalue());
		params.put("npwvalue", t.getNpwvalue());
		params.put("kwhvalue", t.getKwhvalue());
		params.put("remark", t.getRemark());
		return params;
	}

	@Override
	protected RowMapper getRowMapper() {
		// TODO Auto-generated method stub
		return new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				D01ElectricityData edata=new D01ElectricityData();
				edata.setId(rs.getInt("id"));
				edata.setB04id(rs.getInt("b04id"));
				edata.setMonthno(rs.getString("monthno"));
				edata.setHourtime(rs.getString("hourtime"));
				edata.setVvalue(rs.getDouble("vvalue"));
				edata.setAvalue(rs.getDouble("avalue"));
				edata.setYpwvalue(rs.getDouble("ypwvalue"));
				edata.setNpwvalue(rs.getDouble("npwvalue"));
				edata.setKwhvalue(rs.getDouble("kwhvalue"));
				edata.setRemark(rs.getString("remark"));
				
				return edata;
			}
			
		};
	}

	@Override
	public String getInsertSql() {
		// TODO Auto-generated method stub
		String sql="insert into D01ElectricityData(id,b04id,monthno,hourtime,vvalue,avalue,ypwvalue,npwvalue,kwhvalue,remark)values(seq_electricity.nextval,:b04id,:monthno,:hourtime,:vvalue,:avalue,:ypwvalue,:npwvalue,:kwhvalue,:remark)";
		return sql;
	}

	@Override
	public String getUpdateSql() {
		// TODO Auto-generated method stub
		String sql="update D01ElectricityData set b04id=:b04id,monthno=:monthno,hourtime=:hourtime,vvalue=:vvalue,avalue=:avalue,ypwvalue=:ypwvalue,npwvalue=:npwvalue,kwhvalue=:kwhvalue,remark=:remark where id=:id";
		return sql;
	}

}
