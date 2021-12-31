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
	
	<h1>Esito singolo attivit&agrave;</h1>
	
	<br>
	Attivita' eseguita <s:property value="messaggioPostScript" /> 
	<br><br>
	codice attivit&agrave; <s:property value="codscript" /> 
	<br>
	nome del file: <s:property value="scriptFileName" />
	
	
	
	

	<s:include value="/WEB-INF/pages/footer.jsp"></s:include>
	
</body>
</html>