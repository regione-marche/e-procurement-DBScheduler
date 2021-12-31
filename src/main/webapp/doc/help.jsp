
<!DOCTYPE html>

<html>
<head>
<title>DBScheduler</title>
<link rel="stylesheet" type="text/css" href="/DBScheduler/css/style.css">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
</head>

<body>

	<header class="header">
		<img src="/DBScheduler/img/banner_log.png" alt="banner">
	</header>
	<nav class="navigation-box">
		<ul>
			<li><a href="/DBScheduler/do/ScriptJobList.action">Attivit&agrave;</a>
			</li>
			<li><a href="/DBScheduler/do/ScheduleScriptJobList.action">Schedulazioni</a>
			</li>
			<li><a href="/DBScheduler/do/LogsScheduleScriptJob.action">Logs</a>
			</li>
			<li><a href="/DBScheduler/doc/help.jsp">Help Script</a>
			</li>
		</ul>
	</nav>
	
	/DBScheduler/do/ScriptJobList.action

	<h1>Help - </h1>

	<p>
L'elemento principale di DBScheduler sono tuttavia gli script da inserire nei file .etl.xml secondo le specifiche Scriptella.<br/>
La documentazione a riguardo è disponibile in http://scriptella.org/reference/index.html<br/>
<br/>
Fondamentalmente i file XML contengono:<br/>
1. la connessione JDBC al/ai database di interesse<br/>
2. una o più istruzioni query e/o script eventualmente annidate<br/>
<br/>
Si riporta di seguito un esempio di connessione a due database.<br/>
<br/>
<div class="code" >
&lt;connection id="mysqlDurc" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/dbdurc" user="maggioli" password="maggioli12!" /&gt;<br/>
&lt;connection id="oraclePGPL" driver="oracle.jdbc.driver.OracleDriver" url="jdbc:oracle:thin:@localhost:1521:XE" user="elda_pgpl" password="elda_pgpl12!" &gt;<br/>
plsql=true<br/>
statement.separator=/<br/>
statement.separator.singleline=true<br/>
&lt;/connection&gt;<br/>
</div>
<br/>
<br/>
L'istruzione &lt;query&gt; permette di eseguire una SELECT SQL su un database "sorgente". Il risultato viene caricato in memoria.<br/>
Inserendo un'istruzione &lt;script&gt; all'interno di una query, questa verrà eseguita ciclando su tutti i record estratti dalla &lt;query&gt;.<br/>
Esempio:<br/>
<div class=code>
&lt;!-- Replico i dati da MySQL a Oracle --&gt;<br/>
        &lt;query connection-id="mysqlDurc"&gt;<br/>
        &lt;!-- seleziono i dati dei DURC dal DB MySQL --&gt;<br/>
SELECT CFIMP, NOMIMP, ID, null as 'CODIMP', NPROTO, DATRIC_T, DATSCA_T, TIPESI_T, NOTDUR FROM view_durc;<br/>
        <br/>
&lt;!-- per ogni riga viene eseguito lo script nel database di destinazione --&gt;<br/>
        &lt;script connection-id="oraclePGPL"&gt;<br/>
        &lt;!-- Insert delle righe su Oracle --&gt;<br/>
        INSERT INTO IMPDURC_GE(CFIMP,NOMIMP,ID,CODIMP,NPROTO,DATRIC_T,DATSCA_T,TIPESI_T,NOTDUR) values (?CFIMP,?NOMIMP,?ID,?CODIMP,?NPROTO,?DATRIC_T,?DATSCA_T,?TIPESI_T,?NOTDUR)<br/>
       &lt;/script&gt;<br/>
        &lt;/query&gt;<br/>
</div>
Se invece ho solo necessità di eseguire delle operazioni SQL periodicamente, uso l'istruzione &lt;script&gt;<br/>
Esempio:<br/>
<div class="code">
&lt;script connection-id="oraclePGPL"&gt;<br/>
BEGIN
-- inserisce record da personale a TECNI in base al Codice matricola<br/>
INSERT INTO TECNI <br/>
(CODTEC,CGENTEI,COGTEI,NOMETEI,NOMTEC,CFTEC,NOTTEC) <br/>
(SELECT MATRICOLA as CODTEC, MATRICOLA as CGENTEI, COGNOME as COGTEI, NOME as NOMETEI, (COGNOME||' '||NOME) as NOMTEC, CODICE_FISCALE as CFTEC , substr(DES_TITOLO_STUDIO,12,200) <br/>
FROM MATRICOLARIO M LEFT JOIN TECNI T ON M.MATRICOLA=T.CGENTEI WHERE T.CGENTEI IS NULL);<br/>
COMMIT;<br/>
EXCEPTION
WHEN OTHERS THEN<br/>
-- INSERISCO UN MESSAGGIO ALL'UTENTE MANGAER IN CASO KO<br/>
INSERT INTO W_MESSAGE_IN (MESSAGE_ID,MESSAGE_DATE,MESSAGE_SUBJECT,MESSAGE_SENDER_SYSCON,MESSAGE_RECIPIENT_SYSCON) <br/>
SELECT nvl(MAX(MESSAGE_ID),0)+1 as id, SYSDATE as data, 'ERRORE nel caricamento di nuovi dipendenti sull archivio tecnici interni (procedura HR_IMP_TECNI)' as messaggio,50,50 FROM W_MESSAGE_IN; <br/>
-- TERMINA<br/>
COMMIT;<br/>
END;<br/>
/<br/>
&lt;/script&gt;
</div>



	
	



	<footer> Copyright &copy; Maggioli S.p.A. </footer>

</body>
</html>