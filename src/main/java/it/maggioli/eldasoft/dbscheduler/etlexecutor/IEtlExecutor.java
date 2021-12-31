package it.maggioli.eldasoft.dbscheduler.etlexecutor;

import java.net.URL;

/**
 * Interfaccia che si deve implementare per l'eseguzione di uno script etl
 * @author luca.sirri
 *
 */
public interface IEtlExecutor {
	
	public EsitoETLScript executeScript(URL xmlScriptPath) ;
	
	public EsitoETLScript executeScript(String scriptFileName) ;

}
