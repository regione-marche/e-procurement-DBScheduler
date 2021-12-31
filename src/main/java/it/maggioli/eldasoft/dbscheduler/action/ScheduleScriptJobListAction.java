package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobEntity;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value="scheduleScriptJobListAction")
@Scope(value="prototype")
public class ScheduleScriptJobListAction extends ActionSupport {

	private DBSchedulerManager dBSchedulerManager;
	private String codscript;
	private ArrayList<TriggerScriptJobEntity> triggerScriptJobEntities ;
	private String codscriptSelect;
	private ArrayList<String> codscriptList;
	
	@Autowired
	public void setDBSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dBSchedulerManager = dbSchedulerManager;
	}
	
	public String getCodscript() {
		return codscript;
	}
	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}
	

	public ArrayList<TriggerScriptJobEntity> getTriggerScriptJobEntities() {
		return triggerScriptJobEntities;
	}

	public void setTriggerScriptJobEntities(
			ArrayList<TriggerScriptJobEntity> triggerScriptJobEntities) {
		this.triggerScriptJobEntities = triggerScriptJobEntities;
	}

	public void setdBSchedulerManager(DBSchedulerManager dBSchedulerManager) {
		this.dBSchedulerManager = dBSchedulerManager;
	}

	public String getCodscriptSelect() {
		return codscriptSelect;
	}

	public void setCodscriptSelect(String codscriptSelect) {
		this.codscriptSelect = codscriptSelect;
	}

	public ArrayList<String> getCodscriptList() {
		return codscriptList;
	}

	public void setCodscriptList(ArrayList<String> codscriptList) {
		this.codscriptList = codscriptList;
	}
	
	public String getScheduleScriptJobList(){
		
		List<ScriptJobEntity> scriptJobEntities = dBSchedulerManager.getAllScriptJob();
		codscriptList = new ArrayList<String>();
		codscriptList.add("");
		for(ScriptJobEntity jobEntity: scriptJobEntities){
			codscriptList.add(jobEntity.getCodscript());
		}
		
		if(codscript!=null && codscript.length()!=0){			
			triggerScriptJobEntities = (ArrayList<TriggerScriptJobEntity>) dBSchedulerManager.getTriggerScriptJobFromCodScript(codscript);
			codscriptSelect = codscript;
		} else {	
			if(codscriptList.size()!=0){
				codscript = codscriptList.get(0);
			}
			
			triggerScriptJobEntities = (ArrayList<TriggerScriptJobEntity>) dBSchedulerManager.getAllTriggerScriptJob();
		}
		
		return SUCCESS;
	}
}
