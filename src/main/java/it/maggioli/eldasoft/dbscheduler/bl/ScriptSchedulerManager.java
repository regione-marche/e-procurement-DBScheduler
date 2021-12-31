package it.maggioli.eldasoft.dbscheduler.bl;

import it.maggioli.eldasoft.dbscheduler.entity.ScriptJobEntity;
import it.maggioli.eldasoft.dbscheduler.entity.TriggerScriptJobEntity;
import it.maggioli.eldasoft.dbscheduler.etlexecutor.EsitoETLScript;
import it.maggioli.eldasoft.dbscheduler.etlexecutor.IEtlExecutor;
import it.maggioli.eldasoft.dbscheduler.etlexecutor.etlscriptella.ETLScriptella;
import it.maggioli.eldasoft.dbscheduler.quartz.JobEtl;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component(value = "scriptSchedulerManager")
@Scope("prototype")
public class ScriptSchedulerManager {

	private Logger logger = LogManager.getLogger(ScriptSchedulerManager.class);
	private DBSchedulerManager dBSchedulerManager;
	private String scriptFolder;
	private SchedulerFactoryBean quartzFactory;

	@Required
	@Autowired
	public void setQuartzFactory(SchedulerFactoryBean quartzFactory) {
		this.quartzFactory = quartzFactory;
	}

	@Required
	@Autowired
	public void setScriptFolder(String scriptFolder) {
		this.scriptFolder = scriptFolder;
	}

	@Autowired
	public void setDBSchedulerManager(DBSchedulerManager dbSchedulerManager) {
		this.dBSchedulerManager = dbSchedulerManager;
	}

	private Scheduler getScheduler() throws SchedulerException {
		// Scheduler scheduler = SchedulerQuartzManager.getSchedulerInstance();
		Scheduler scheduler = quartzFactory.getScheduler();

		// inserisco l'indicazione della cartella in cui risiedono i file script
		scheduler.getContext().put("scriptFolder", scriptFolder);

		return scheduler;
	}

	private JobDetail getJobDetail(String nameScriptFilename) {

		return getJobDetail(nameScriptFilename, null);
	}

	private JobDetail getJobDetail(String nameScriptFilename, String groupName) {

		if (groupName == null) {
			groupName = "group1";
		}

		return JobBuilder.newJob(JobEtl.class).withIdentity(nameScriptFilename, groupName).build();
	}

	private CronTrigger getCronTrigger(String name, String group, String cronexp) throws RuntimeException{

		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(name, group)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronexp))
				.build();

		return trigger;
	}

	/**
	 * Elimina un job dallo scheduler, lo script viene individuato attraverso il name e grupo
	 * per il group è il codice script che è kp del db, montre per il mane è la concatenazione
	 * del codscript e l'id del trigger che corrisponde alla pk del db
	 *
	 * @param triggerId id del trigger a db
	 * @param codscript codice dello script a db
	 * @throws SchedulerException
	 */
	public void deleteJobScheduleTrigger(Integer triggerId, String codscript) throws SchedulerException{
		Scheduler scheduler = getScheduler();
		String name = StringUtils.substring(triggerId + "-" + codscript, 0, 25);
		String group = codscript ;
		JobKey jobKey = new JobKey(name, group);
		scheduler.deleteJob(jobKey );
		logger.info("Tolta Schedulazione " + name + " " + group );

	}

	/***
	 * Aggiungo una schedulazione a partire da un TriggerScriptJobEntity
	 *
	 * @param triggerScriptJobEntity
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void addJobScheduleTrigger(TriggerScriptJobEntity triggerScriptJobEntity) throws SchedulerException {

		String name = StringUtils.substring(triggerScriptJobEntity.getId() + "-" + triggerScriptJobEntity.getCodscript(), 0, 25);
		String group = triggerScriptJobEntity.getCodscript() ;
		ScriptJobEntity jobscript = dBSchedulerManager.getScriptJobEntity(triggerScriptJobEntity.getCodscript());
		String filename = jobscript.getFilename();
		String cronexp = triggerScriptJobEntity.getCronexp();

		addJobScheduleTrigger(name, group, filename, cronexp);
	}

	/**
	 *  Aggiungo una schedulazione
	 *
	 * @param name
	 * @param group
	 * @param filename
	 * @param cronexp
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	private void addJobScheduleTrigger(String name, String group, String filename, String cronexp) throws SchedulerException {
		logger.info("Aggiunta Schedulazione " + name + " " + group);

		Scheduler scheduler = getScheduler();

		// Ottengo la coppia jobDetail e CronTrigger

		// inserisco nell'ahsh map interlo all'oggetto jobdetail il nome del file
		// per essere disponibile quando deve essere eseguito lo script
		JobDetail jobDetail = getJobDetail(name, group);
		jobDetail.getJobDataMap().put("filename", filename);
		logger.info("Creazione JobDetail e Trigger (group: " + group
				+ " name: " + name + ")");
		CronTrigger trigger = getCronTrigger(name, group, cronexp );
		scheduler.scheduleJob(jobDetail, trigger);

		logger.info("Aggiunto nello Scheduler JobDetail e Trigger (group: "
				+ group + " name: " + name + " filename" + filename +" )");
	}

	/**
	 * Avvio tutti gli script schedulati nel db
	 *
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void startAllScript() throws SchedulerException {

		logger.info("Inizio Caricamento tutti gli script");
		Scheduler scheduler = getScheduler();
		scheduler.clear();

		// lista degli script censiti nel db
		List<ScriptJobEntity> listaScript = this.dBSchedulerManager.getAllScriptJob();

		for (ScriptJobEntity scriptJobEntity : listaScript) {

			// lista delle schedulazioni/trigger di uno script/job
			String codscript = scriptJobEntity.getCodscript();
			List<TriggerScriptJobEntity> triggerScriptJobEntities = this.dBSchedulerManager.getTriggerScriptJobFromCodScriptActive(codscript);

			for (TriggerScriptJobEntity tsjEntity : triggerScriptJobEntities) {
				// per ogni schedulazione del inserita nel db mi creo un job e relativa schedulazione di quarz
				// come group utilizzo il codice identificativo dello script del db
				// come nome lo stesso codice concatenato all'id (progressivo) della schedualzione a db
				String group = tsjEntity.getCodscript();
				String name = tsjEntity.getCodscript() + tsjEntity.getId();

				logger.info("Creazione JobDetail e Trigger (group: " + group
						+ " name: " + name + ")");

				// Ottengo la coppia jobDetail e CronTrigger
				JobDetail jobDetail = getJobDetail(name, group);
				// inserisco nell'ahsh map interlo all'oggetto jobdetail il nome del file
				// per essere disponibile quando deve essere eseguito lo script
				jobDetail.getJobDataMap().put("filename", scriptJobEntity.getFilename());

				logger.info("JobDetail filename: " + scriptJobEntity.getFilename() + " (group: " + group + " name: " + name + ")");

				CronTrigger trigger;
				try {
					trigger = getCronTrigger(name, group, tsjEntity.getCronexp());
					// aggiungo la coppia trigger e job allo scheduler
					scheduler.scheduleJob(jobDetail, trigger);
					logger.info("Aggiunto nello Scheduler JobDetail e Trigger (group: " + group + " name: " + name + ")");
				} catch (RuntimeException e) {
					logger.error("Trigger con problemi, CronExpression non valida, non aggiunto allo Scheduler (group: " + group + " name: " + name + ")", e);
				}

			}

		}

		logger.info("Avvio scheduler");
		scheduler.start();
		logger.info("Scheduler Avviato ");

	}

	public EsitoETLScript execute(String scriptFileName) {
		IEtlExecutor etlScriptella = new ETLScriptella(scriptFolder);
		EsitoETLScript esito = etlScriptella.executeScript(scriptFileName);

		return esito;

	}

	public void startScheduleJob(String scriptFilename) throws SchedulerException {

			Scheduler scheduler = getScheduler();

            JobDetail jobDetail = getJobDetail(scriptFilename);
            logger.info("Creato JobDetail " + scriptFilename );

            Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("myTrigger", "group1")
            .startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10)
                .repeatForever())
            .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("Aggiunto JobDetail " + scriptFilename + " allo scheduler");

            // and start it off
            scheduler.start();
            logger.info( "Scheduler Iniziato : " + scriptFilename);

	}


}
