package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value = "scheduleList")
@Scope("prototype")
public class ScheduleList extends ActionSupport {

	private List<TriggerScriptJobEntity> triggerScriptJobList;
	private DBSchedulerManager dbSchedulerManager;

	@Autowired
	public void setDbSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dbSchedulerManager = dbSchedulerManager;
	}

	public List<TriggerScriptJobEntity> getTriggerScriptJobList() {
		return triggerScriptJobList;
	}

	public void setTriggerScriptJobList(
			List<TriggerScriptJobEntity> triggerScriptJobList) {
		this.triggerScriptJobList = triggerScriptJobList;
	}


	public String mostraLista() {
		// Ottengo la lista delle schedulazioni dalla query
		triggerScriptJobList = this.dbSchedulerManager.getAllTriggerScriptJob();

		return SUCCESS;
	}

}
