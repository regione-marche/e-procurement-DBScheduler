package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value = "scriptJobListAction")
@Scope("prototype")
public class ScriptJobListAction extends ActionSupport{
	
	private String codscript;
	private List<ScriptJobEntity> scriptJobEntities;
	private DBSchedulerManager dBSchedulerManager;

	public String getCodscript() {
		return codscript;
	}
	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}
	
	public List<ScriptJobEntity> getScriptJobEntities() {
		return scriptJobEntities;
	}
	public void setScriptJobEntities(List<ScriptJobEntity> scriptJobEntities) {
		this.scriptJobEntities = scriptJobEntities; 
	}

	@Autowired
	public void setDBSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dBSchedulerManager = dbSchedulerManager;
	}
	
	/**
	 * @return lista di tutti gli ScriptJob
	 */
	public String getScriptListJob(){		
		scriptJobEntities = dBSchedulerManager.getAllScriptJob();		
		return SUCCESS;
	}
}
