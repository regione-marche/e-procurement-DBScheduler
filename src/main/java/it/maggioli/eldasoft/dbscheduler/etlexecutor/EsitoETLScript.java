package it.maggioli.eldasoft.dbscheduler.etlexecutor;

/**
 * Oggetto che contiene le informazioni post script 
 * 
 * @author luca.sirri
 *
 */
public class EsitoETLScript {
	
	private int esito;
	private String messaggio;
	
	/**	  
	 * @return 	1=successo  
	 * 			0=errore
	 */
	public int getEsito() {
		return esito;
	}
	/**
	 * 
	 * @param esito 1=successo  
	 * 				0=errore
	 */
	public void setEsito(int esito) {
		this.esito = esito;
	}
	/**
	 * in caso di errore viene messo anche il messaggio dell'exection
	 * @return messaggio di ritorno dallo script
	 */
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
	

}
