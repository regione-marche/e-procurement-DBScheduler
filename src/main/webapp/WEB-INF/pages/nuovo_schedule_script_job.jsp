<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>

<html>
<head>
	<title>DBScheduler</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" >
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" /> <%-- per forzare ultima versione di Internet Explorer --%>	
</head>
<body>

	<s:include value="/WEB-INF/pages/header.jsp"></s:include>
	<s:include value="/WEB-INF/pages/navigation.jsp"></s:include>
	
	<s:if test="hasActionErrors()">
	   <div class="errors">
	      <s:actionerror/>
	   </div>
	</s:if>
	
	<h1>Schedulazione dell'attivit&agrave; <s:property value="codscript"/></h1></h1>
	<div class="input_data">
	<s:form action="ScheduleScriptJobNuovo" namespace="script_sql">
	
		
		
		<s:textfield label="cron expression" name="cronexp" maxlength="20" />	

		<s:hidden name="codscript" value="%{codscript}"  />
		<s:submit value="salva" name="submit" />	
	
	</s:form>
	</div>

							Formato, caratteri speciali ed esempi
						</td>
						<td class="valore-dato">
							<table class="quartz">
								<tr class="intestazione">
									<td colspan="5">Formato della pianificazione</td>
								</tr>
								<tr class="intestazione">
									<td>Posizione</td>
									<td>Nome del campo</td>
									<td>Obbligatorio&nbsp;?</td>
									<td>Valori possibili</td>
									<td>Caratteri speciali disponibili</td>
								</tr>
								<tr>
									<td>1</td>
									<td>Secondi</td>
									<td>Si</td>
									<td>0-59</td>
									<td>, - * /</td>
								</tr>
								<tr>
									<td>2</td>
									<td>Minuti</td>
									<td>Si</td>
									<td>0-59</td>
									<td>, - * /</td>
								</tr>								
								<tr>
									<td>3</td>
									<td>Ore</td>
									<td>Si</td>
									<td>0-23</td>
									<td>, - * /</td>
								</tr>
								<tr>
									<td>4</td>
									<td>Giorni del mese</td>
									<td>Si</td>
									<td>1-31</td>
									<td>, - * ? / L W</td>
								</tr>
								<tr>
									<td>5</td>
									<td>Mesi</td>
									<td>Si</td>
									<td>1-12 oppure JAN-DEC</td>
									<td>, - * /</td>
								</tr>
								<tr>
									<td>6</td>
									<td>Giorni della settimana</td>
									<td>Si</td>
									<td>1-7 oppure SUN-SAT</td>
									<td>, - * ? / L #</td>
								</tr>
								<tr>
									<td>7</td>
									<td>Anni</td>
									<td>No</td>
									<td>vuoto, 1970-2099</td>
									<td>, - * /</td>
								</tr>									
							</table>
							<br>
							<table class="quartz">
								<tr class="intestazione">
									<td colspan="3">Caratteri speciali</td>
								</tr>							
								<tr class="intestazione">
									<td>Carattere</td>
									<td>Descrizione</td>
									<td>Esempio</td>
								</tr>
								<tr>
									<td>*</td>
									<td>Tutti i valori, utilizzato per selezionare tutti i valori di un campo</td>
									<td>Nel campo dei minuti "*" significa "ogni minuto"</td>
								</tr>
								<tr>
									<td>?</td>
									<td>Nessun valore specifico</td>
									<td>Se indicato nel campo dei giorni del mese permette di eseguire la pianificazione ignorando il giorno del mese</td>
								</tr>
								<tr>
									<td>-</td>
									<td>Intervallo, permette di indicare un intervallo di valori</td>
									<td>Nel campo delle ore "10-12" significa "ore 10, 11 e 12"</td>
								</tr>
								<tr>
									<td>,</td>
									<td>Lista di valori, permette di specificare una lista di valori distinti </td>
									<td>Nel campo dei giorni del mese "5,15" permette l'esecuzione solo nei giorni 5 e 15.</td>
								</tr>
								<tr>
									<td>/</td>
									<td>Incrementi, permette di definire un incremento da un valore fisso</td>
									<td>Nel campo dei secondi "0/15" significa "secondi 0, 15, 30 e 45"</td>
								</tr>
							</table>
							<br>
							Per maggiori informazioni consultare la guida in linea 
							<br>
							<a class="link-generico" target="_blank" href="http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/crontrigger">
								http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/crontrigger
							</a>
							<br>
							<br>
							<table class="quartz">
								<tr class="intestazione">
									<td colspan="2">Alcuni esempi di pianificazione</td>
								</tr>
								<tr class="intestazione">
									<td>Pianificazione</td>
									<td>Significato</td>
								</tr>
								<tr>
									<td>0/30 * * ? * *</td>
									<td>Esecuzione ogni trenta secondi di ogni minuto di ogni ora di ogni giorno</td>
								<tr>
								<tr>
									<td>0 0 0 1 * ?</td>
									<td>Esecuzione alla mezzanotte del primo giorno del mese</td>
								<tr>
								<tr>
									<td>0 0 * ? * *</td>
									<td>Esecuzione ad ogni ora</td>
								<tr>
								<tr>
									<td>0 0/5 * * * ? </td>
									<td>Esecuzione ogni 5 minuti di ogni giorno (ore 0.00, 0.05, 0.10, ...) </td>
								<tr>
								<tr>
									<td>0 59 7-20/1 * * ?</td>
									<td>Esecuzione ogni giorno, ogni ora dalle 7:59 alle 20:59</td>
								<tr>																
								<tr>
									<td>0 0 0 1 1 ? 2099</td>
									<td>Esecuzione nel 2099, in pratica la pianificazione non &egrave; abilitata</td>
								<tr>	
							</table>

	
	
	<s:include value="/WEB-INF/pages/footer.jsp"></s:include>
</body>
</html>	