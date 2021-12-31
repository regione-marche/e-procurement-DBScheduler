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
	
	<h1>Attivit&agrave; schedulabili</h1>
	
	<s:if test="hasActionErrors()">
	   <div class="errors">
	      <s:actionerror/>
	   </div>
	</s:if>
	
	
	<a href="ScriptJobNuovoEdit.action">[nuova attivit&agrave;]</a>

	<table class="w3-table w3-striped w3-bordered">
	 <thead>
		<tr>
			<th class="width_10">codice attivit&agrave;</th>
			<th class="width_15">nome del file</th>
			<th class="width_35">descrizione</th>
			<th class="width_30">azioni</th>
	
		</tr>
	</thead>
	<tbody>
		<s:iterator value="scriptJobEntities" status="rowstatus">
		<tr>
			<td class="width_10"><s:property value="codscript"/> </td>
			<td class="width_15"><s:property value="filename"/> </td>
			<td  class="width_35"><s:property value="descrizione"/> </td>			
			<td  class="width_30">
			<a href="ScriptJobDettaglio.action?codscript=<s:property value="codscript"/>">[dettaglio]</a>			
			<a href="StartSingleScript.action?codscript=<s:property value="codscript"/>">[esegui]</a>
			<a href="ScheduleScriptJobList.action?codscript=<s:property value="codscript"/>">[schedulazione]</a>
			</td>
		</tr>
		</s:iterator>
	</tbody>	
	</table>

	<s:include value="/WEB-INF/pages/footer.jsp"></s:include>
	
</body>
</html>