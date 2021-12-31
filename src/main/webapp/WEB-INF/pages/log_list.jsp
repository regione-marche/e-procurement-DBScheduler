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
	
	
	
	<h1>Logs </h1>
	
	<s:form action="LogsScheduleScriptJobFiltered" namespace="script_sql">
		
		<s:select label="codice attivita'"		
			list="codscriptList"
			name="codscript"
			value="codscript" />
		

		
		<s:select label="Esito attivita'"
			list="#{'-1':' ', '1':'Successo', '0':'Fallita'}"
			name="esito"
			value="-1" />
			
		<s:textfield label="Da [gg/mm/aaaa] " name="dataDa" />
		
		<s:textfield label="A [gg/mm/aaaa] " name="dataA" />	
		
		<s:submit value="filtra" name="submit" />
	
	</s:form>
	
	
	<table>
	<thead>
		<tr>
			<th width="width_20">data schedulazione</th>
			<th class="width_10">codice attivit&agrave;</th>
			<th class="width_10">esito</th>
			<th class="width_60">messaggio</th>
		</tr>	
	</thead>
	<tbody>
	<s:iterator value="logs" status="rowstatus">
		<tr>
			<td width="width_20" ><s:property value="data"/> </td>
			<td class="width_10"><s:property value="codscript"/> </td>
			<s:if test="%{esito==true}">
				<td class="esito_ok">Successo</td>
			</s:if>
			<s:else>
			    <td class="esito_ko">Fallita</td>
			</s:else>		
			
			<td class="width_60" ><s:property value="messaggio"/> </td>
	
		</tr>
	</s:iterator>
	</tbody>
	</table>
	
	<s:include value="/WEB-INF/pages/footer.jsp"></s:include>
	

</body>
</html>			