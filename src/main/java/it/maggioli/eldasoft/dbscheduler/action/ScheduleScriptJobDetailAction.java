package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.bl.ScriptSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value="scheduleScriptJobDetailAction")
@Scope(value="prototype")
public class ScheduleScriptJobDetailAction extends ActionSupport {
	
	private Logger logger = LoggerFactory.getLogger(ScheduleScriptJobDetailAction.class);

	private DBSchedulerManager dBSchedulerManager;
	private ScriptSchedulerManager scriptSchedulerManager;
	private TriggerScriptJobEntity triggerScriptJobEntity;

	@Autowired
	public void setScriptSchedulerManager(
			ScriptSchedulerManager scriptSchedulerManager) {
		this.scriptSchedulerManager = scriptSchedulerManager;
	}
	@Autowired
	public void setDBSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dBSchedulerManager = dbSchedulerManager;
	}
	private String id;
	private String codscript;
	private String cronexp;

	private String target = SUCCESS;
	
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
	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}
	
	public String updateScheduleScriptJob(){
		
		//prima di aggiungere al db la cronexp controlla la validita'
		if(CronExpression.isValidExpression(cronexp)){				
			Integer idTrigger = Integer.valueOf(id);			
			dBSchedulerManager.updateScheduleScriptJob(idTrigger,cronexp );
			createTriggerEntity(idTrigger);
			try {
				if (scriptSchedulerManager.isJobPresent(idTrigger, codscript)) {
					scriptSchedulerManager.deleteJobScheduleTrigger(idTrigger, codscript);
					scriptSchedulerManager.addJobScheduleTrigger(triggerScriptJobEntity);
				}
			} catch (Exception e) {
				logger.error("Errore:", e);
				target = ERROR;
			}
		} else {
			// Se l'espressione non e' valida comunico il problema
			String  anErrorMessage = "La schedulazione non puo' essere inserita, l'espressione (" + cronexp +  ") non e' valida";
			logger.info(anErrorMessage);
			this.addActionError(anErrorMessage);
			this.target = "cronExpressionError";
		}
		return this.target;
	}

	private void createTriggerEntity(Integer idTrigger) {
		triggerScriptJobEntity = new TriggerScriptJobEntity();
		triggerScriptJobEntity.setId(idTrigger);
		triggerScriptJobEntity.setCodscript(codscript);
		triggerScriptJobEntity.setCronexp(cronexp);
	}

}
