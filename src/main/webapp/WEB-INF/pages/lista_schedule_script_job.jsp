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
	
	<h1>Schedulazioni 
	<s:if test="%{codscript.length() !=0}">	
   		dell'attivit&agrave; <s:property value="codscript"/>
	</s:if>
	
	</h1>
	
	<s:if test="hasActionErrors()">
	   <div class="errors">
	      <s:actionerror/>
	   </div>
	</s:if>
	
	<s:form action="ScheduleScriptJobList" namespace="script_sql">
		
		<s:select label="codice attivita'"		
			list="codscriptList"
			name="codscript"
			value="codscript" />
		<s:submit value="filtra" name="submit" />
	
	</s:form>
	
	<s:if test="%{triggerScriptJobEntities.isEmpty()}">
		nessuna schedulazione presente<br>	
			<s:if test="%{codscript.length() !=0}">		
				<a href="ScheduleScriptJobNuovoEdit.action?codscript=<s:property value="codscript"/>">[nuova schedulazione]</a>
			</s:if>
			<s:else>
				<a href="#" onclick="alert('Selezionare una attivit&agrave;')">[nuova schedulazione]</a>	
			</s:else>	
	</s:if>
	
	<s:else>
	
		<s:if test="%{codscript.length() !=0}">		
			<a href="ScheduleScriptJobNuovoEdit.action?codscript=<s:property value="codscript"/>">[nuova schedulazione]</a>
		</s:if>
		<s:else>
			<a href="#" onclick="alert('Selezionare una attivit&agrave;')">[nuova schedulazione]</a>	
		</s:else>
		
		<table>
		<thead>
		<tr>
			<th>id</th>
			<th>codice attivit&agrave;</th>
			<th>cron expression</th>
			<th>stato</th>
			<th>azioni</th>
		</tr>
		</thead>
		<tbody>	
			<s:iterator value="triggerScriptJobEntities" status="rowstatus">
			<tr>
				<td><s:property value="id"/> </td>
				<td><s:property value="codscript"/> </td>
				<td><s:property value="cronexp"/> </td>
				<td>							
					<s:if test="%{stato==1}">
						Attivo
					</s:if>
					<s:else>
					    Non Attivo
					</s:else>
				</td>			
				<td>
					<a href="ScheduleScriptJobDettaglio.action?id=<s:property value="id"/>">[dettaglio]</a>			
					<a href="ScheduleScriptJobCambiaStato.action?id=<s:property value="id"/>&codscript=<s:property value="codscript"/>" onclick="return confirm('Vuoi cambiare lo stato della schedulazione?')" >[cambia stato]</a>						
					<a href="ScheduleScriptJobCancella.action?id=<s:property value="id"/>&codscript=<s:property value="codscript"/>" onclick="return confirm('Cancellare la schedulazione?')">[cancella]</a>				
				</td>
			</tr>
			</s:iterator>		
		</tbody>
		
		</table>


	</s:else>


	<s:include value="/WEB-INF/pages/footer.jsp"></s:include>
</body>
</html>