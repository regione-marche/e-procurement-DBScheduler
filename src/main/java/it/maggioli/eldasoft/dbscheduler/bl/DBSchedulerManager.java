package it.maggioli.eldasoft.dbscheduler.bl;

import it.maggioli.eldasoft.dbscheduler.dao.DBSchedulerMapper;
import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobEntity;
import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobLogsEntity;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;


/**
 * Oggetto per la gestione delle query attraverso il Mapper di MyBatis (DBSchedulerMapper)
 * @author luca.sirri
 *
 */
@Component(value = "dBSchedulerManager")
public class DBSchedulerManager implements Serializable {
	
	private DBSchedulerMapper dbSchedulerMapper;
	

	@Required
	@Autowired
	public void setDbSchedulerMapper(DBSchedulerMapper dbSchedulerMapper) {
		this.dbSchedulerMapper = dbSchedulerMapper;
	}
	
	
	public List<TriggerScriptJobEntity> getAllTriggerScriptJob(){
		return this.dbSchedulerMapper.getAllTriggerScriptJob();			
	}
	
	public List<ScriptJobEntity> getAllScriptJob(){		
		return this.dbSchedulerMapper.getAllScriptJob();
	}
	
	public List<TriggerScriptJobEntity>  getTriggerScriptJobFromCodScript(String codscript){
		return this.dbSchedulerMapper.getTriggerScriptJobFromCodScript(codscript);
	}
	
	/**
	 * Ritorna tutte le scchedulazioni attive (stato>0) di uno script
	 * @param codscript codice dello script
	 * @return lista delle schedulazioni
	 */
	public List<TriggerScriptJobEntity> getTriggerScriptJobFromCodScriptActive(String codscript){
		return dbSchedulerMapper.getTriggerScriptJobFromCodScriptActive(codscript);
	}
	
	
	public void addScriptJobLog(Integer esito, String messaggio, String codscript, String triggercode, Timestamp date){
		dbSchedulerMapper.addScriptJobLog(esito, messaggio, codscript, triggercode,date);		
	}

	public List<String> getAllcodscript(){
		return dbSchedulerMapper.getAllcodscript();
	}

	public ScriptJobEntity getScriptJobEntity(String codscript) {
		return dbSchedulerMapper.getScriptJobEntity(codscript);
	}

	public void addScriptJobEntity(String codscript, String filename, String descrizione){
		dbSchedulerMapper.addScriptJobEntity(codscript, filename, descrizione);
	}

	public void updateScriptJobEntity(String codscript, String filename, String descrizione) {
		dbSchedulerMapper.updateScriptJobEntity(codscript, filename, descrizione);
	}


	public TriggerScriptJobEntity getTriggerScriptJobFromId(int id) {
		return dbSchedulerMapper.getTriggerScriptJobFromId(id);
	}


	public void updateScheduleScriptJob(int id, String cronexp) {
		dbSchedulerMapper.updateScheduleScriptJob( id,  cronexp) ;
	}

	/**
	 * Aggiunge una schedulazione a db
	 * @param codscript codice dello script pk
	 * @param cronexp espressione cron per il crontrigger
	 * @param stato indica se la schedulazione e' attiva o no
	 */
	public void addScheduleScriptJob(String codscript, String cronexp, int stato) {
		dbSchedulerMapper.addScheduleScriptJob( codscript,  cronexp,  stato);		
	}

	public void updateScheduleScriptJobStatus(int id, int stato){
		dbSchedulerMapper.updateScheduleScriptJobStatus( id,  stato);
	}

	public void deleteScheduleScriptJob(Integer idTrigger) {
		dbSchedulerMapper.deleteScheduleScriptJob(idTrigger);
	}

	public ArrayList<ScriptJobLogsEntity> getLogsListAll() {
		return  dbSchedulerMapper.getLogsListAll();
	}
	
	public ArrayList<ScriptJobLogsEntity> getLogsList(String codscript, Timestamp dataDa, Timestamp dataA, Integer esito) {
		return  dbSchedulerMapper.getLogsList(codscript, dataDa, dataA, esito);
	}


}
