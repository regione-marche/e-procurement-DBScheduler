package it.maggioli.eldasoft.dbscheduler.action;

import it.maggioli.eldasoft.dbscheduler.bl.DBSchedulerManager;
import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobLogsEntity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.routines.CalendarValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value="logsScheduleScriptJobListAction")
@Scope(value="prototype")
public class LogsScheduleScriptJobListAction extends ActionSupport {
	
	private Logger logger = LogManager.getLogger(LogsScheduleScriptJobListAction.class);
	
	private ArrayList<ScriptJobLogsEntity>  logs;
	private DBSchedulerManager dBSchedulerManager;
	private String codscript;
	private String dataDa, dataA;
	private Timestamp dataDaDataFormat;
	private Timestamp dataADataFormat;
	private Integer esito;
	private List<String> codscriptList;

	
	@Autowired
	public void setDBSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dBSchedulerManager = dbSchedulerManager;
	}
	
	public String getLogListAll(){				
		logs = dBSchedulerManager.getLogsListAll();
		return SUCCESS;
	}


	public String getLogList () {
		
		Timestamp timestampDataDa = null ;
		Timestamp timestampDataA = null ;
		
		try {
			timestampDataA = getTimestamp(dataA);
		} catch (ParseException e) {
			logger.error("Errore di inserimento data A", e);
			this.addFieldError("dataA", "Data A, Errore di formato");
		}
		
		try {
			timestampDataDa = getTimestamp(dataDa);
		} catch (ParseException e) {
			logger.error("Errore di inserimento data Da", e);
			this.addFieldError("dataDa", "Data Da, Errore di formato");
		}
		
		logs = dBSchedulerManager.getLogsList(codscript, timestampDataDa , timestampDataA, esito);
		
		return SUCCESS;
		
	}

	private Timestamp getTimestamp(String data) throws ParseException {
		if(data!=null && data.length()!=0){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    Date parsedDate = dateFormat.parse(data);
		    Timestamp dataDaTimestamp = new Timestamp(parsedDate.getTime());
			return dataDaTimestamp;
		} else {
			return null;
		}
		
	}
	
	public ArrayList<ScriptJobLogsEntity> getLogs() {
		return logs;
	}
	
	public void setLogs(ArrayList<ScriptJobLogsEntity> logs) {
		this.logs = logs;
	}

	public String getCodscript() {
		return codscript;
	}

	public void setCodscript(String codscript) {
		this.codscript = codscript;
	}

	public String getDataDa() {
		return dataDa;
	}

	public void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}

	public String getDataA() {
		return dataA;
	}

	public void setDataA(String dataA) {
		this.dataA = dataA;
	}

	public Integer getEsito() {
		return esito;
	}

	public void setEsito(Integer esito) {
		this.esito = esito;
	}

	public List<String> getCodscriptList() {
		
		codscriptList = new ArrayList<String>();
		codscriptList.add("");
		codscriptList.addAll(dBSchedulerManager.getAllcodscript());
		
		return codscriptList;
	}

	public void setCodscriptList(List<String> codscriptList) {
		this.codscriptList = codscriptList;
	}


	
	

}
