--SET search_path = public, pg_catalog;

SET statement_timeout = 0;
-- decommentare la riga seguente in caso di lancio da interprete di comando direttamente da linux
--SET client_encoding = 'LATIN1';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

-- primo aggiornamento

CREATE OR REPLACE FUNCTION aggiornamento() 
	RETURNS void AS
$$
BEGIN

	IF NOT EXISTS (SELECT TABLENAME FROM PG_TABLES WHERE UPPER(TABLENAME)='ELDAVER') THEN

		CREATE TABLE eldaver (
			codapp VARCHAR(10) not null,
			numver VARCHAR(10),
			datvet TIMESTAMP,
			msg VARCHAR(2000),
			primary key (codapp)
		);

	END IF;
	
	IF (SELECT COUNT(*) FROM ELDAVER WHERE codapp = 'DBSC') = 0 THEN
		INSERT INTO eldaver (codapp, numver, datvet, msg) VALUES ('DBSC', '1.2.0', CURRENT_TIMESTAMP, NULL);
	END IF;

    
END;
$$
LANGUAGE 'plpgsql' ;

select * from aggiornamento();
drop function aggiornamento();

-- 1.2.0to1.3.0

CREATE OR REPLACE FUNCTION aggiornamento() 
	RETURNS void AS
$$
BEGIN

    IF (select count(*) = 1 from eldaver where codapp = 'DBSC' AND numver = '1.2.0') THEN

		ALTER TABLE script_job_logs ALTER COLUMN messaggio TYPE TEXT;  

		-- AGGIORNAMENTO DELLE VERSIONI
		UPDATE eldaver SET numver = '1.3.0', datvet = CURRENT_TIMESTAMP WHERE codapp = 'DBSC' AND numver = '1.2.0';

	END IF;
END;
$$
LANGUAGE 'plpgsql' ;

select * from aggiornamento();
drop function aggiornamento();
-- 1.3.0to1.4.0
CREATE OR REPLACE FUNCTION aggiornamento() 
	RETURNS void AS
$$
BEGIN

    IF (select count(*) = 1 from eldaver where codapp = 'DBSC' AND numver = '1.3.0') THEN
		-- AGGIORNAMENTO DELLE VERSIONI
		UPDATE eldaver SET numver = '1.4.0', datvet = CURRENT_TIMESTAMP WHERE codapp = 'DBSC' AND numver = '1.3.0';

	END IF;
END;
$$
LANGUAGE 'plpgsql' ;

select * from aggiornamento();
drop function aggiornamento();

