package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value = "scriptJobAddAction")
@Scope("prototype")
public class ScriptJobAddAction extends ActionSupport{
	
	private Logger logger = LogManager.getLogger(ScriptJobAddAction.class);
	
	private String target = SUCCESS;
	
	private DBSchedulerManager dBSchedulerManager;
	private String descrizione;
	private String codscript;
	private String filename;
	
	public DBSchedulerManager getdBSchedulerManager() {
		return dBSchedulerManager;
	}
	@Autowired
	public void setdBSchedulerManager(DBSchedulerManager dBSchedulerManager) {
		this.dBSchedulerManager = dBSchedulerManager;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCodscript() {
		return codscript;
	}
	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String addScriptJobEntity(){
		
		if(codscript!=null && codscript.length()!=0 && 
		  filename!=null && filename.length()!=0){			
			dBSchedulerManager.addScriptJobEntity(codscript, filename, descrizione);
		} else {
			// il filename e codscript deve essere valorizzato
		
			String fieldName ;
			String  anErrorMessage ; 
			if(codscript!=null && codscript.length()!=0 ){
				fieldName = "codscript";
			} else {
				fieldName = "filename";
			}
			
			anErrorMessage = "Il campo " + fieldName + " deve essere valorizzato";			
			
			this.addFieldError(fieldName, anErrorMessage)   ;
			this.target = "nofieldError";
		}
		
		return this.target;
	}
	

}
