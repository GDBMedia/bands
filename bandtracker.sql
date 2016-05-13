--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bands; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE bands (
    id integer NOT NULL,
    name character varying,
    description character varying,
    genre character varying
);


ALTER TABLE bands OWNER TO "Guest";

--
-- Name: bands_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE bands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bands_id_seq OWNER TO "Guest";

--
-- Name: bands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE bands_id_seq OWNED BY bands.id;


--
-- Name: venues; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE venues (
    id integer NOT NULL,
    name character varying,
    location character varying,
    minor boolean
);


ALTER TABLE venues OWNER TO "Guest";

--
-- Name: venues_bands; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE venues_bands (
    id integer NOT NULL,
    venue_id integer,
    band_id integer
);


ALTER TABLE venues_bands OWNER TO "Guest";

--
-- Name: venues_bands_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE venues_bands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE venues_bands_id_seq OWNER TO "Guest";

--
-- Name: venues_bands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE venues_bands_id_seq OWNED BY venues_bands.id;


--
-- Name: venues_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE venues_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE venues_id_seq OWNER TO "Guest";

--
-- Name: venues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE venues_id_seq OWNED BY venues.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY bands ALTER COLUMN id SET DEFAULT nextval('bands_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY venues ALTER COLUMN id SET DEFAULT nextval('venues_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY venues_bands ALTER COLUMN id SET DEFAULT nextval('venues_bands_id_seq'::regclass);


--
-- Data for Name: bands; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY bands (id, name, description, genre) FROM stdin;
2	Moon Tiger	Moon Tiger is a band from Portland Oregon featuring Alison Cohn, Meagan Schreiber, Alicia J. Rose & Taylor Hill.	Rock & Roll
1	TWENTY ØNE PILØTS	an American musical duo that originates from Columbus, Ohio. The band was formed in 2009 and consists of Tyler Joseph and Josh Dun.	Alternative hip hop
\.


--
-- Name: bands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('bands_id_seq', 8, true);


--
-- Data for Name: venues; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY venues (id, name, location, minor) FROM stdin;
1	Moda Center	1 N Center Ct St, Portland, OR 97227	t
2	Doug Fir Bar	830 E. Burnside St., Portland, OR 97214	f
3	Mississippi Studios	3939 N. Mississippi, Portland, OR 97227	f
4	Wonder Ballroom	128 N.E. Russell St., Portland, OR 97212	t
5	Jimmy Mak’s	221 N.W. 10th Ave., Portland, OR 97209	f
6	Revolution Hall	1300 S.E. Stark St., Portland, OR 97214	t
7	Crystal Ballroom	1332 W. Burnside St., Portland, OR 97209	t
8	Arlene Schnitzer Concert Hall	1037 S.W. Broadway, Portland, OR 97205	t
9	McMenamins Edgefield	2126 S.W. Halsey, Troutdale, OR 97060	f
10	Aladdin Theater	3017 SE Milwaukie Avenue	f
11	Holocene	1001 SE Morrison Street	f
12	Rontom's	600 East Burnside Street	t
13	The Know	2026 NE Alberta Street	f
14	Valentines	232 SW Ankeny Street	f
15	The Thirsty Lion	10205 SW Washington Square Rd. Tigard, OR 97223	t
\.


--
-- Data for Name: venues_bands; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY venues_bands (id, venue_id, band_id) FROM stdin;
2	2	2
3	3	2
4	12	2
5	13	2
6	14	2
17	1	1
\.


--
-- Name: venues_bands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('venues_bands_id_seq', 23, true);


--
-- Name: venues_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('venues_id_seq', 15, true);


--
-- Name: bands_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY bands
    ADD CONSTRAINT bands_pkey PRIMARY KEY (id);


--
-- Name: venues_bands_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY venues_bands
    ADD CONSTRAINT venues_bands_pkey PRIMARY KEY (id);


--
-- Name: venues_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY venues
    ADD CONSTRAINT venues_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

