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
	<h1>Dettaglio attivit&agrave;</h1>
	
	<s:if test="hasActionErrors()">
	   <div class="errors">
	      <s:actionerror/>
	   </div>
	</s:if>	
	
	<div class="data_input">
	<s:form action="ScriptJobModifica" namespace="script_sql">
	
		codice dell'attivit&agrave; <s:property value="codscript"/>
	
		<s:textfield label="nome del file xml" name="filename" value="%{scriptJobEntity.filename}" maxlength="400" />					
		<br/>
		<s:textarea label = "descrizione attivita'" name="descrizione"  value="%{scriptJobEntity.descrizione}"/>
		<s:hidden name="codscript" value="%{scriptJobEntity.codscript}"  />
		<s:submit value="salva" name="submit" />
	
	</s:form>
	</div>
	
	<s:include value="/WEB-INF/pages/footer.jsp"></s:include>

</body>
</html>