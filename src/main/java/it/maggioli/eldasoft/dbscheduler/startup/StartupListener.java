package it.maggioli.eldasoft.dbscheduler.startup;

import it.maggioli.eldasoft.dbscheduler.bl.ScriptSchedulerManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component(value="startupListener")
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private ScriptSchedulerManager scriptSchedulerManager;
	private Logger logger = LogManager.getLogger(StartupListener.class);
	
	@Autowired
	public void setScriptSchedulerManager(
			ScriptSchedulerManager scriptSchedulerManager) {
		this.scriptSchedulerManager = scriptSchedulerManager;
	}



	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		try {
			scriptSchedulerManager.startAllScript();
		} catch (SchedulerException e) {
			logger.error("Errore start up applicazione" , e);
		}
		
	}

}
