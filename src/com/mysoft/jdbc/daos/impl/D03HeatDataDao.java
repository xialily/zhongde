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


import com.mysoft.entities.D03HeatData;
import com.mysoft.jdbc.daos.IBaseDao;
/**
 * 电表dao
 * 
 * @author yangfan
 *
 */
@Repository("d03HeatDataDao")
@SuppressWarnings("all")
public  class D03HeatDataDao extends BaseDao<D03HeatData>  implements IBaseDao<D03HeatData> {
	
@Resource
	private NamedParameterJdbcTemplate jdbctemplate;
	
public D03HeatDataDao(){
	this.setTablename("D03HeatData");
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
	public Map getMapbyT(D03HeatData t) {
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("id", t.getId());
		params.put("b04id", t.getB04id());
		params.put("monthno", t.getMonthno());
		params.put("hourtime", t.getHourtime());
		params.put("currentvalue", t.getCurrentvalue());
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
				D03HeatData edata=new D03HeatData();
				edata.setId(rs.getInt("id"));
				edata.setB04id(rs.getInt("b04id"));
				edata.setMonthno(rs.getString("monthno"));
				edata.setHourtime(rs.getString("hourtime"));
				edata.setCurrentvalue(rs.getDouble("currentvalue"));
				edata.setTotalstream(rs.getDouble("totalstream"));
				edata.setRemark(rs.getString("remark"));
				
				return edata;
			}
			
		};
	}

	@Override
	public String getInsertSql() {
		// TODO Auto-generated method stub
		String sql="insert into D03HeatData(id,b04id,monthno,hourtime,currentvalue,totalstream,remark)values(seq_heat.nextval,:b04id,:monthno,:hourtime,:currentvalue,:totalstream,:remark)";
		return sql;
	}

	@Override
	public String getUpdateSql() {
		// TODO Auto-generated method stub
		String sql="update D03HeatData set b04id=:b04id,monthno=:monthno,hourtime=:hourtime,currentvalue=:currentvalue,totalstream=:totalstream,remark=:remark where id=:id";
		return sql;
	}

}
