package it.maggioli.eldasoft.dbscheduler.entity;

import java.io.Serializable;

public class ScriptJobEntity implements Serializable {
	
	private String descrizione;
	private String codscript;
	private String filename;
	
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
	
	

	
}
