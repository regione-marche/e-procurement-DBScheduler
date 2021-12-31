package it.maggioli.eldasoft.dbscheduler.quartz;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.etlexecutor.EsitoETLScript;
import it.maggioli.eldasoft.dbscheduler.etlexecutor.IEtlExecutor;
import it.maggioli.eldasoft.dbscheduler.etlexecutor.etlscriptella.ETLScriptella;
import it.maggioli.eldasoft.dbscheduler.startup.SpringAppContext;

import java.net.URL;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Job che viene eseguito da Quartz, implementa anche l'interfaccia ETL per eseguire gli script 
 * 
 * @author luca.sirri
 *
 */
public class JobEtl implements IEtlExecutor , Job {
	
	private String scriptFolder;
	private Logger logger = LogManager.getLogger(JobEtl.class);
	private String scriptFilename;	


	public String getScriptFolder() {
		return scriptFolder;
	}

	public void setScriptFolder(String scriptFolder) {
		this.scriptFolder = scriptFolder;
	}

	public String getScriptFilename() {
		return scriptFilename;
	}

	public void setScriptFilename(String scriptFilename) {
		this.scriptFilename = scriptFilename;
	}


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		Timestamp now = new Timestamp(System.currentTimeMillis());		
		String codscript = context.getJobDetail().getKey().getGroup();
		String triggercode = context.getJobDetail().getKey().getName();
		
		// Recupero le informazioni necessarie
		scriptFilename = context.getJobDetail().getJobDataMap().getString("filename");  
		
		// stringa identificativa del job per gli script
		String messaggio = "[codscript: " + codscript + ", triggercode " + triggercode + ", filename " + scriptFilename +" ]" ;

		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(SpringAppContext.getServletContext());
		DBSchedulerManager dBSchedulerManager = (DBSchedulerManager) wac.getBean("dBSchedulerManager");;
		try {
			
			scriptFolder = context.getScheduler().getContext().getString("scriptFolder") ;
			
			// recupero il dbmanager, viene messo nel hashmap che utilizza l'oggetto Jobdetail di Quartz 
			logger.info("inizio Job " + messaggio) ;		
			
			// eseguo lo script tramite Scriptella e ottengo l'oggetto esito
			EsitoETLScript esito = executeScript(scriptFilename);


			// messaggio = messaggio + " esito " + esito.getEsito();
			if(esito.getEsito()<=0){
				messaggio = messaggio + " - " + esito.getMessaggio();
			}
			
			// traccio l'esito nel log a db
			dBSchedulerManager.addScriptJobLog(esito.getEsito(), messaggio, codscript, triggercode, now) ;
			
			logger.info("fine Job " + messaggio) ;	
			
		} catch (SchedulerException e) {
			logger.error("Errore in fase di esecuzione job " + messaggio, e);			
			dBSchedulerManager.addScriptJobLog(0, messaggio, codscript, triggercode, now) ;
		}
	}

	@Override
	public EsitoETLScript executeScript(URL xmlScriptPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EsitoETLScript executeScript(String scriptFileName) {
		
		logger.info("inizio Script ETL " + scriptFileName ) ;
		
		ETLScriptella etlScriptella = new ETLScriptella(scriptFolder);
		EsitoETLScript esito = etlScriptella.executeScript(scriptFileName);

		logger.info("fine Script ETL " + scriptFileName ) ;
		
		return esito;
	}

}
