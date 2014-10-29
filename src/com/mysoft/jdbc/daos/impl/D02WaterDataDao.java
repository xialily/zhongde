package com.mysoft.jdbc.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import com.mysoft.entities.D02WaterData;
import com.mysoft.jdbc.daos.IBaseDao;
/**
 * 电表dao
 * 
 * @author yangfan
 *
 */
@Repository("d02WaterDataDao")
@SuppressWarnings("all")
public  class D02WaterDataDao extends BaseDao<D02WaterData>  implements IBaseDao<D02WaterData> {
	
@Resource
	private NamedParameterJdbcTemplate jdbctemplate;
	
public D02WaterDataDao(){
	this.setTablename("D02WaterData");
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
	public Map getMapbyT(D02WaterData t) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("id", t.getId());
		params.put("b04id", t.getB04id());
		params.put("monthno", t.getMonthno());
		params.put("hourtime", t.getHourtime());
		params.put("currentstream", t.getCurrentstream());
		params.put("totalstream", t.getTotalstream());
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
				D02WaterData edata=new D02WaterData();
				edata.setId(rs.getInt("id"));
				edata.setB04id(rs.getInt("b04id"));
				edata.setMonthno(rs.getString("monthno"));
				edata.setHourtime(rs.getString("hourtime"));
				edata.setCurrentstream(rs.getDouble("currentstream"));
				edata.setTotalstream(rs.getDouble("totalstream"));
				edata.setRemark(rs.getString("remark"));
				
				return edata;
			}
			
		};
	}

	@Override
	public String getInsertSql() {
		// TODO Auto-generated method stub
		String sql="insert into D02WaterData(id,b04id,monthno,hourtime,currentstream,totalstream,remark)values(seq_water.nextval,:b04id,:monthno,:hourtime,:currentstream,:totalstream,:remark)";
		return sql;
	}

	@Override
	public String getUpdateSql() {
		// TODO Auto-generated method stub
		String sql="update D02WaterData set b04id=:b04id,monthno=:monthno,hourtime=:hourtime,currentstream=:currentstream,totalstream=:totalstream,remark=:remark where id=:id";
		return sql;
	}
}
