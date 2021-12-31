package it.maggioli.eldasoft.dbscheduler.entity;

import java.io.Serializable;

public class TriggerScriptJobEntity  implements Serializable  {
	
	private String codscript;
	private Integer id;
	private Integer stato;
	private String cronexp;
	
	public String getCodscript() {
		return codscript;
	}
	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStato() {
		return stato;
	}
	public void setStato(Integer stato) {
		this.stato = stato;
	}
	public String getCronexp() {
		return cronexp;
	}
	public void setCronexp(String cronexp) {
		this.cronexp = cronexp;
	}
	
	
	
}
