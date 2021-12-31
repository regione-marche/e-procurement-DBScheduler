package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value = "scriptJobDetailAction")
@Scope("prototype")
public class ScriptJobDetailAction extends ActionSupport {
	
	private Logger logger = LogManager.getLogger(ScriptJobDetailAction.class);
	
	private DBSchedulerManager dBSchedulerManager;
	private String descrizione;
	private String codscript;
	private String filename;
	private ScriptJobEntity scriptJobEntity;
	private String target = SUCCESS ;
	
	@Autowired
	public void setDBSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dBSchedulerManager = dbSchedulerManager;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getCodscript() {
		return codscript;
	}
	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}
	public ScriptJobEntity getScriptJobEntity() {
		return scriptJobEntity;
	}
	public void setScriptJobEntity(ScriptJobEntity scriptJobEntity) {
		this.scriptJobEntity = scriptJobEntity;
	}
	
	/**
	 * @return un oggetto ScriptJob a partire da codscript passato da get
	 */
	public String getScriptJobEntityFromCodScript(){
		this.scriptJobEntity = dBSchedulerManager.getScriptJobEntity(codscript);
		return SUCCESS;
	}
	
	public String updateScriptJobEntity(){
		if(filename!=null && filename.length()!=0){
			dBSchedulerManager.updateScriptJobEntity(codscript, filename, descrizione);
			this.scriptJobEntity = dBSchedulerManager.getScriptJobEntity(codscript);			
		} else {		
			// il filename e codscript deve essere valorizzato
			String  anErrorMessage = "Il campo filename deve essere valorizzato";
			logger.info(anErrorMessage);
			String fieldName = "filename";
			this.addFieldError(fieldName, anErrorMessage)   ;
			this.target = "nofieldError";
		}
		
		return this.target;
	}
	

}
