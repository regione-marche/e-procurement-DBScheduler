package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value="scheduleScriptJobDetailAction")
@Scope(value="prototype")
public class ScheduleScriptJobDetailAction extends ActionSupport {
	
	private Logger logger = LogManager.getLogger(ScheduleScriptJobDetailAction.class);
	
	private DBSchedulerManager dBSchedulerManager; 
	private TriggerScriptJobEntity triggerScriptJobEntity;
	
	private String id;
	private String cronexp;
	
	private String target = SUCCESS;
	
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
	public String getCronexp() {
		return cronexp;
	}
	
	public void setCronexp(String cronexp) {
		this.cronexp = cronexp;
	}

	public TriggerScriptJobEntity getTriggerScriptJobEntity() {
		return triggerScriptJobEntity;
	}

	public void setTriggerScriptJobEntity(
			TriggerScriptJobEntity triggerScriptJobEntity) {
		this.triggerScriptJobEntity = triggerScriptJobEntity;
	}
	public String getScheduleScriptJobDettaglio(){
		Integer idTrigger = Integer.valueOf(id);
		triggerScriptJobEntity = dBSchedulerManager.getTriggerScriptJobFromId(idTrigger);
		
		this.target = SUCCESS;
		return this.target ;
	}
	
	public String updateScheduleScriptJob(){
		
		//prima di aggiungere al db la cronexp controlla la validita'
		if(CronExpression.isValidExpression(cronexp)){				
			Integer idTrigger = Integer.valueOf(id);			
			dBSchedulerManager.updateScheduleScriptJob(idTrigger,cronexp );
		} else {
			// Se l'espressione non e' valida comunico il problema
			String  anErrorMessage = "La schedulazione non puo' essere inserita, l'espressione (" + cronexp +  ") non e' valida";
			logger.info(anErrorMessage);
			this.addActionError(anErrorMessage);
			this.target = "cronExpressionError";
		}
		return this.target;
	}

}
