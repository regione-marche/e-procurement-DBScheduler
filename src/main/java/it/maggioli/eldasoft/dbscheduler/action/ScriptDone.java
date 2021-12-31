package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.bl.ScriptSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobEntity;
import it.maggioli.eldasoft.dbscheduler.etlexecutor.EsitoETLScript;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value = "scriptDone")
@Scope("prototype")
public class ScriptDone extends ActionSupport{

	private Logger logger = LogManager.getLogger(ScriptDone.class);
	private ScriptSchedulerManager scriptSchedulerManager;
	private DBSchedulerManager dBSchedulerManager;
	private EsitoETLScript esito;
	private String messaggioPostScript;
	private String codscript;
	private String scriptFileName;
	
	@Autowired
	public void setDBSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dBSchedulerManager = dbSchedulerManager;
	}

	@Autowired
	public void setScriptSchedulerManager(
			ScriptSchedulerManager scriptSchedulerManager) {
		this.scriptSchedulerManager = scriptSchedulerManager;
	}

	public String getMessaggioPostScript() {
		return messaggioPostScript;
	}

	public void setMessaggioPostScript(String messaggioPostScript) {
		this.messaggioPostScript = messaggioPostScript;
	}


	public String getCodscript() {
		return codscript;
	}

	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}

	public EsitoETLScript getEsito() {
		return esito;
	}

	public void setEsito(EsitoETLScript esito) {
		this.esito = esito;
	}
	
	public String getScriptFileName() {
		return scriptFileName;
	}

	public void setScriptFileName(String scriptFileName) {
		this.scriptFileName = scriptFileName;
	}

	/**
	 * esegue un singolo script 
	 * @return
	 */
	public String executeScript(){
		
		ScriptJobEntity scriptJob = dBSchedulerManager.getScriptJobEntity(codscript);
		scriptFileName = scriptJob.getFilename();
		
		if(scriptFileName!=null && scriptFileName.length()!=0){
			esito = scriptSchedulerManager.execute(scriptFileName);
		} else {
			esito = new EsitoETLScript();
			esito.setEsito(0);
			esito.setMessaggio("il nome del file passato è nullo");
		}
		
		messaggioPostScript = "con esito ";
		if(esito.getEsito()==1){
			messaggioPostScript = messaggioPostScript + "POSITIVO" ;
		} else {
			messaggioPostScript = messaggioPostScript + "NEGATIVO : " + esito.getMessaggio() ;
		}
		
		Timestamp now = new Timestamp(System.currentTimeMillis());		
		dBSchedulerManager.addScriptJobLog(esito.getEsito(), "singola esecuzione " + esito.getMessaggio(), codscript, "singola esecuzione", now) ;
		
		return SUCCESS;
		
	}


}
