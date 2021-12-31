package it.maggioli.eldasoft.dbscheduler.etlexecutor.etlscriptella;

import it.maggioli.eldasoft.dbscheduler.etlexecutor.EsitoETLScript;
import it.maggioli.eldasoft.dbscheduler.etlexecutor.IEtlExecutor;

import java.io.File;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import scriptella.execution.EtlExecutor;

public class ETLScriptella implements IEtlExecutor {
	
	private String scriptFolder;
	private Logger logger = LogManager.getLogger(ETLScriptella.class);
	
	/**
	 * Set del folder in cui i file xml risiedono
	 * @param scriptFolder
	 */
	public ETLScriptella(String scriptFolder) {
		this.scriptFolder = scriptFolder;
	}

	/***
	 * Esegue lo script ETL utilizzando Scriptella 
	 * @param xmlScriptPath url del file in cui c'e' lo script etl
	 */
	@Override
	public EsitoETLScript executeScript(URL xmlScriptPath)  {
		
		EsitoETLScript esito = new EsitoETLScript();
		
		try {
			logger.info("Inizio script " + xmlScriptPath);
			
			EtlExecutor.newExecutor(xmlScriptPath).execute();			
			
			esito.setEsito(1);
			// esito.setMessaggio("OK");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			logger.error("Errore script " + xmlScriptPath);			
			esito.setEsito(0);
			esito.setMessaggio(e.getMessage());
		}
		finally{
			logger.info("Fine script " + xmlScriptPath);
			return esito;
		}
		
		
	}

	/**
	 * Esegue lo script ETL utilizzando Scriptella 
	 * @param scriptFileName nome del file in cui c'e' lo script etl
	 */
	@Override
	public EsitoETLScript executeScript(String scriptFileName)  {
		
		EsitoETLScript esito = new EsitoETLScript();
		
		try {
			String scriptFilePath = this.scriptFolder + scriptFileName;
			logger.info("Inizio script " + scriptFileName);
			
			EtlExecutor.newExecutor(new File(scriptFilePath)).execute();
			
			esito.setEsito(1);
			esito.setMessaggio("OK");			

		} catch (Exception e) {

			e.printStackTrace();
			
			logger.error("Errore script " + scriptFileName);			
			esito.setEsito(0);
			esito.setMessaggio(e.getMessage());
			
		} finally{
			logger.info("Fine script " + scriptFileName);
			return esito;
		}
		
	}

}
