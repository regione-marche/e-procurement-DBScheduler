package it.maggioli.eldasoft.dbscheduler.dao;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.ibatis.annotations.Select;



public class QueryBuilder {
	
	public String getLogsProvider(Map parameters) {
		// @Select("Select id,codscript,triggercode, esito,messaggio, data  FROM script_job_logs  order by codscript, id desc")
		StringBuilder query = new StringBuilder();

		query.append("Select id,codscript,triggercode, esito,messaggio, data  FROM script_job_logs ");
		
		String codscript = (String) parameters.get("codscript") ;
		Integer esito = (Integer) parameters.get("esito") ;
		Timestamp dataDa = (Timestamp) parameters.get("dataDa") ;
		Timestamp dataA = (Timestamp) parameters.get("dataA") ;
		
		if(esito<0) {
			esito = null;
		}
		
		String whereCondition="";
		
		if(codscript!=null && codscript.length()!=0){
			whereCondition = whereCondition + " codscript = #{codscript}"  ;
		}
		
		if(esito!=null) {
			if(whereCondition!=null && whereCondition.length()!=0) {
				whereCondition = whereCondition + " and " ;
			} 
			whereCondition = whereCondition + " esito =  #{esito}  "  ;
		}
		
		if(dataDa!=null){
			if(whereCondition!=null && whereCondition.length()!=0) {
				whereCondition = whereCondition + " and " ;
			}
			whereCondition = whereCondition + " data >= #{dataDa} "  ;
		}
		
		if(dataA!=null){
			if(whereCondition!=null && whereCondition.length()!=0) {
				whereCondition = whereCondition + " and " ;
			}
			whereCondition = whereCondition + " data <= #{dataA} "  ;
		}
		
		if (whereCondition.length()!=0){
			whereCondition = " WHERE " + whereCondition ;
			query.append(whereCondition); 
		}
		query.append(" order by id desc ");
		
		return query.toString();
	}

}
