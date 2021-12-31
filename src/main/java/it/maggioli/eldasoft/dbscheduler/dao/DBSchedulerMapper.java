package it.maggioli.eldasoft.dbscheduler.dao;

import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobEntity;
import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobLogsEntity;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

public interface DBSchedulerMapper {
	
	// SELECT QUERY //
	
	@Select("SELECT descrizione,codscript,filename	FROM script_job ORDER BY codscript")
	@Results({
		@Result(property = "codscript", column = "codscript"),
		@Result(property = "filename", column = "filename"),
		@Result(property = "descrizione", column = "descrizione")
	})
	public List<ScriptJobEntity> getAllScriptJob();
	
	@Select("SELECT codscript	FROM script_job ORDER BY codscript")
	public List<String> getAllcodscript();
	
	@Select("SELECT id, codscript, cronexp, stato FROM trigger_script_job ORDER BY codscript, id")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "codscript", column  = "codscript"),
			@Result(property = "cronexp", column = "cronexp"),
			@Result(property = "stato", column = "stato")
	})
	public List<TriggerScriptJobEntity> getAllTriggerScriptJob();

	@Select("SELECT id, codscript, cronexp, stato FROM trigger_script_job WHERE codscript = #{codscript} ORDER BY id")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "codscript", column  = "codscript"),
			@Result(property = "cronexp", column = "cronexp"),
			@Result(property = "stato", column = "stato")
	})
	public List<TriggerScriptJobEntity> getTriggerScriptJobFromCodScript(String codscript);
	
	@Select("SELECT id, codscript, cronexp, stato FROM trigger_script_job WHERE codscript = #{codscript} AND stato>0 ORDER BY id")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "codscript", column  = "codscript"),
			@Result(property = "cronexp", column = "cronexp"),
			@Result(property = "stato", column = "stato")
	})
	public List<TriggerScriptJobEntity> getTriggerScriptJobFromCodScriptActive(String codscript);
	
	@Insert("INSERT INTO script_job_logs (esito, messaggio, codscript, triggercode, data) " +
			"VALUES (#{esito}, #{messaggio}, #{codscript}, #{triggercode}, #{data} ) ")
	public void addScriptJobLog(@Param("esito")Integer esito, @Param("messaggio")String messaggio, 
			@Param("codscript")String codscript, @Param("triggercode")String triggercode, @Param("data")Timestamp data);

	
	@Select("SELECT descrizione, codscript, filename FROM script_job  WHERE codscript = #{codscript} ORDER BY codscript")
	@Results({
		@Result(property = "codscript", column = "codscript"),
		@Result(property = "filename", column = "filename"),
		@Result(property = "descrizione", column = "descrizione")
	})	
	public ScriptJobEntity getScriptJobEntity(String codscript);

	@Update("UPDATE script_job SET filename=#{filename}, descrizione=#{descrizione} " +
			"WHERE codscript=#{codscript}")			
	public void updateScriptJobEntity(@Param("codscript")String codscript, @Param("filename")String filename, @Param("descrizione")String descrizione);
	
	@Insert("INSERT INTO script_job (codscript, filename, descrizione) " +
			"VALUES (#{codscript}, #{filename}, #{descrizione})")
	public void addScriptJobEntity(@Param("codscript")String codscript, @Param("filename")String filename, @Param("descrizione")String descrizione);

	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "codscript", column  = "codscript"),
		@Result(property = "cronexp", column = "cronexp"),
		@Result(property = "stato", column = "stato")
	})
	@Select("SELECT id, codscript, cronexp, stato FROM trigger_script_job WHERE id = #{id} ORDER BY id")
	public TriggerScriptJobEntity getTriggerScriptJobFromId(int id);


	/**
	 * query per la modifica di una schedulazione
	 * @param id
	 * @param cronexp
	 */
	@Update("UPDATE trigger_script_job SET cronexp=#{cronexp} " +
			"WHERE id=#{id}")
	public void updateScheduleScriptJob(@Param("id")int id, @Param("cronexp")String cronexp);

	/**
	 * query pee una nuova schedulazione 
	 * @param codscript codice attivita'
	 * @param cronexp cron expression per quartz
	 * @param stato attivazione della schedulazione
	 */
	@Insert("INSERT INTO trigger_script_job (codscript, cronexp, stato) " +
			"VALUES (#{codscript}, #{cronexp}, #{stato})")
	public void addScheduleScriptJob(@Param("codscript")String codscript, @Param("cronexp")String cronexp, @Param("stato")int stato);

	/**
	 * cancellazione di una schedulazione
	 * @param id
	 */
	@Delete("DELETE FROM trigger_script_job WHERE id=#{id}")
	public void deleteScheduleScriptJob(@Param("id")int id);

	/**
	 * query di aggiornamento di una schedulazione
	 * @param id pk della schedulazione
	 * @param stato
	 */
	@Update("UPDATE trigger_script_job SET stato=#{stato} " +
			"WHERE id=#{id}")
	public void updateScheduleScriptJobStatus(@Param("id")int id, @Param("stato")int stato);

	/**
	 * query per la lista dei logs
	 * @return
	 */
	@Select("Select id,codscript,triggercode, esito,messaggio, data  FROM script_job_logs  order by id desc")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "triggercode", column = "triggercode"),
		@Result(property = "codscript", column  = "codscript"),
		@Result(property = "esito", column = "esito"),
		@Result(property = "messaggio", column = "messaggio"),
		@Result(property = "data", column = "data")
	})
	public ArrayList<ScriptJobLogsEntity> getLogsListAll();

	/**
	 * query per la lista dei log filtrati, la query reale si trova nel oggetto QueryBuilder
	 * @param codscript
	 * @param dataDa
	 * @param dataA
	 * @param esito
	 * @return
	 */
	 @SelectProvider(type = QueryBuilder.class, method = "getLogsProvider") 
	 public ArrayList<ScriptJobLogsEntity> getLogsList(@Param("codscript")String codscript, 
	 		@Param("dataDa")Timestamp dataDa, @Param("dataA")Timestamp dataA, @Param("esito")Integer esito );
	

	
}
