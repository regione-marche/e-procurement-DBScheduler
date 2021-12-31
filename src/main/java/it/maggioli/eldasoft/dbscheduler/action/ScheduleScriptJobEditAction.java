package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.bl.ScriptSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;


@Component(value="scheduleScriptJobEditAction")
@Scope(value="prototype")
public class ScheduleScriptJobEditAction extends ActionSupport{
	
	private Logger logger = LogManager.getLogger(ScheduleScriptJobAddAction.class);

	private DBSchedulerManager dBSchedulerManager; 
	private TriggerScriptJobEntity triggerScriptJobEntity;
	private ScriptSchedulerManager scriptSchedulerManager;
	
	private String codscript;
	
	private Integer stato;
	private String cronexp;
	
	private String target = SUCCESS;
	
	@Autowired
	public void setDBSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dBSchedulerManager = dbSchedulerManager;
	}
	
	@Autowired
	public void setScriptSchedulerManager(
			ScriptSchedulerManager scriptSchedulerManager) {
		this.scriptSchedulerManager = scriptSchedulerManager;
	}

	public TriggerScriptJobEntity getTriggerScriptJobEntity() {
		return triggerScriptJobEntity;
	}

	public void setTriggerScriptJobEntity(
			TriggerScriptJobEntity triggerScriptJobEntity) {
		this.triggerScriptJobEntity = triggerScriptJobEntity;
	}
	
	public String getCodscript() {
		return codscript;
	}
	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}

	public String getCronexp() {
		return cronexp;
	}

	public void setCronexp(String cronexp) {
		this.cronexp = cronexp;
	}
	
	public Integer getStato() {
		return stato;
	}
	public void setStato(Integer stato) {
		this.stato = stato;
	}

	public String getTarget() {
		return this.target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

	

//	/**
//	 * Aggiungo una schedulazione al database
//	 * @return
//	 */
//	public String addScheduleScriptJob(){		
//		//prima di aggiungere al db la cronexp controlla la validita'
//		if(CronExpression.isValidExpression(cronexp)){			
//			// aggiungo sempre la schedulazione non attiva (stato=0)		
//			dBSchedulerManager.addScheduleScriptJob(codscript,cronexp,0);
//		} else {
//			// Se l'espressione non e' valida comunico il problema
//			String  anErrorMessage = "La schedulazione non puo' essere inserita, l'espressione (" + cronexp +  ") non e' valida";
//			logger.info(anErrorMessage);
//			this.addActionError(anErrorMessage);
//			this.target = "cronExpressionError";
//			//this.hasActionErrors()
//		}
//		return this.target;
//	}
	
	public String editScheduleScriptJob(){
		return this.target;
	}

}
