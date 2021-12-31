package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.bl.ScriptSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;



@Component(value="scheduleScriptJobCancelAction")
@Scope(value="prototype")
public class ScheduleScriptJobCancelAction extends ActionSupport{
	
	private String id;
	private DBSchedulerManager dBSchedulerManager; 	
	private ScriptSchedulerManager scriptSchedulerManager;
	private Logger logger = LogManager.getLogger(ScheduleScriptJobCancelAction.class);

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
