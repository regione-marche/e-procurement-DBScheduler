package it.maggioli.eldasoft.dbscheduler.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ScriptJobLogsEntity  implements Serializable  {
	
	private String id;
	private Integer esito;
	private String messaggio;
	private String codscript;
	private String triggercode;
	private Timestamp data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getEsito() {
		return esito;
	}
	public void setEsito(Integer esito) {
		this.esito = esito;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public String getCodscript() {
		return codscript;
	}
	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}
	public String getTriggercode() {
		return triggercode;
	}
	public void setTriggercode(String triggercode) {
		this.triggercode = triggercode;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp date) {
		this.data = date;
	}
	
	

}
