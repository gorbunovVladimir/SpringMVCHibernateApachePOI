CREATE USER vladimir WITH PASSWORD '123456';
CREATE DATABASE hospitalisation ENCODING = 'UTF8' owner vladimir;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public to vladimir;
CREATE EXTENSION pgcrypto;
\connect hospitalisation

CREATE TABLE human_bed( 
id_human_bed SERIAL,
name_human VARCHAR(100),
bdate date,
edate date,
CONSTRAINT human_bed_pkey PRIMARY KEY (id_human_bed)
)
WITH (oids = false);
ALTER TABLE public.human_bed
  OWNER TO vladimir;
--GRANT  ALL privileges ON table human_bed TO vladimir;

  