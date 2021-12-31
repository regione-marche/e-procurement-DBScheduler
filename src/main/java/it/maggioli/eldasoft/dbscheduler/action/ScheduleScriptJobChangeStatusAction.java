package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.bl.ScriptSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;



@Component(value="scheduleScriptJobChangeStatusAction")
@Scope(value="prototype")
public class ScheduleScriptJobChangeStatusAction extends ActionSupport{
	
	private String id;
	private DBSchedulerManager dBSchedulerManager; 
	private ScriptSchedulerManager scriptSchedulerManager;
	private Logger logger = LogManager.getLogger(ScheduleScriptJobChangeStatusAction.class);
	private String target;

	@Autowired
	public void setScriptSchedulerManager(
			ScriptSchedulerManager scriptSchedulerManager) {
		this.scriptSchedulerManager = scriptSchedulerManager;
	}
	
	@Autowired
	public void setDBSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dBSchedulerManager = dbSchedulerManager;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * cambio dello stato della schedulazione da attiva a non attiva 
	 * e viceversa 
	 * 
	 * @return
	 */
	public String changeScheduleScriptJob(){
		int statoDaCambiare=0;
		TriggerScriptJobEntity triggerScriptJobEntity = dBSchedulerManager.getTriggerScriptJobFromId(Integer.valueOf(id)) ;		
		try {
			if(triggerScriptJobEntity!=null){
				
					
				if (triggerScriptJobEntity.getStato()==0){
					statoDaCambiare=1;
				}
				
				// combio lo stato a livello db		
				dBSchedulerManager.updateScheduleScriptJobStatus(Integer.valueOf(id), statoDaCambiare);			
				
				// Cambio lo stato nello scheduler
				if(statoDaCambiare>0){
					// Devo attivare la schedulazione
					scriptSchedulerManager.addJobScheduleTrigger(triggerScriptJobEntity);
				} else {
					// Devo togliere la schedualzione
					scriptSchedulerManager.deleteJobScheduleTrigger(triggerScriptJobEntity.getId(), triggerScriptJobEntity.getCodscript());
				}
				
				
				
			}
		} catch (SchedulerException e) {
			String anErrorMessage = "Problema in fase di cambio stato della Schedulazione"; 
			// ripristino lo stato iniziale a db
			dBSchedulerManager.updateScheduleScriptJobStatus(Integer.valueOf(id), triggerScriptJobEntity.getStato());
			logger.error(anErrorMessage, e);
			this.addActionError(anErrorMessage);
			
			return SUCCESS;
			
		}
		
		return SUCCESS;
	}
	
	
	
	public String deleteScheduleScriptJob(){		
		//cancellazione della schedulazione dallo scheduler di quartz
		TriggerScriptJobEntity triggerScriptJobEntity = dBSchedulerManager.getTriggerScriptJobFromId(Integer.valueOf(id)) ;
		try {
			scriptSchedulerManager.deleteJobScheduleTrigger(triggerScriptJobEntity.getId(), triggerScriptJobEntity.getCodscript());
			// cancellazione della schedulazione da db
			dBSchedulerManager.deleteScheduleScriptJob( Integer.valueOf(id));
		} catch (Exception e) {
			String anErrorMessage = "Problema in fase di cancellazione Schedulazione"; 
			logger.error(anErrorMessage, e);
			this.addActionError(anErrorMessage);
		}

		return SUCCESS;
	}

}
