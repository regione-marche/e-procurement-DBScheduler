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
	
	<h1>Nuova attivit&agrave;</h1>
	
	<s:if test="hasActionErrors()">
	   <div class="errors">
	      <s:actionerror/>
	   </div>
	</s:if>
	
	<div class="data_input">
	<s:form action="ScriptJobNuovo" namespace="script_sql">
	
		<s:textfield label="Codice attivita'" name="codscript" maxlength="25" />					

		<s:textfield label="Nome del file XML" name="filename" maxlength="400" />	

		<s:textarea label="Descrizione" name="descrizione" />

		<s:submit value="salva" name="submit" />
	
	</s:form>
	</div>
	
	<br/>
	<a href="/DBScheduler/doc/esempio_etl.xml" target="_new">Esempio di script ETL</a> 
	<br/>
	
	Nella pagina help ci sono delle indicazioni su come deve essere scritto un file xml per gli script. 
	
	<s:include value="/WEB-INF/pages/footer.jsp"></s:include>
</body>
</html>