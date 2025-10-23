--
-- PostgreSQL database dump
--

\restrict Gd0ejAjZoX9dlWqZ22elzbSN3ZEnua7gq8STH5of4kXVpxU3KHsDYVQif1yXvaV

-- Dumped from database version 16.10 (Debian 16.10-1.pgdg13+1)
-- Dumped by pg_dump version 16.10 (Debian 16.10-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: appointment; Type: TABLE; Schema: public; Owner: plexsalud_test_user
--

CREATE TABLE public.appointment (
    uuid uuid NOT NULL,
    created_at timestamp(6) with time zone,
    date timestamp(6) with time zone NOT NULL,
    status character varying(255) NOT NULL,
    updated_at timestamp(6) with time zone,
    doctor_uuid uuid,
    patient_uuid uuid
);


ALTER TABLE public.appointment OWNER TO plexsalud_test_user;

--
-- Name: doctors; Type: TABLE; Schema: public; Owner: plexsalud_test_user
--

CREATE TABLE public.doctors (
    uuid uuid NOT NULL,
    created_at timestamp(6) without time zone,
    full_name character varying(255) NOT NULL,
    specialty character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    user_uuid uuid
);


ALTER TABLE public.doctors OWNER TO plexsalud_test_user;

--
-- Name: nurses; Type: TABLE; Schema: public; Owner: plexsalud_test_user
--

CREATE TABLE public.nurses (
    uuid uuid NOT NULL,
    created_at timestamp(6) without time zone,
    full_name character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    user_uuid uuid
);


ALTER TABLE public.nurses OWNER TO plexsalud_test_user;

--
-- Name: patients; Type: TABLE; Schema: public; Owner: plexsalud_test_user
--

CREATE TABLE public.patients (
    uuid uuid NOT NULL,
    created_at timestamp(6) without time zone,
    full_name character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    user_uuid uuid
);


ALTER TABLE public.patients OWNER TO plexsalud_test_user;

--
-- Name: users; Type: TABLE; Schema: public; Owner: plexsalud_test_user
--

CREATE TABLE public.users (
    uuid uuid NOT NULL,
    created_at timestamp(6) without time zone,
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255),
    updated_at timestamp(6) without time zone,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['DOCTOR'::character varying, 'PATIENT'::character varying, 'NURSE'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO plexsalud_test_user;

--
-- Data for Name: appointment; Type: TABLE DATA; Schema: public; Owner: plexsalud_test_user
--

COPY public.appointment (uuid, created_at, date, status, updated_at, doctor_uuid, patient_uuid) FROM stdin;
f442e984-a57a-4885-b8aa-51582f2ac7a0	2025-10-23 14:59:50.091291+00	2025-10-24 06:00:00+00	CREATED	2025-10-23 14:59:50.091348+00	26399ff0-9785-48a9-972c-f8f0ce53d309	2e78ba63-ebb7-4934-b9cd-8fbf75364e2d
623cd34b-b014-4995-b64d-f28cb7263b46	2025-10-23 15:00:31.971438+00	2025-10-25 07:00:00+00	CREATED	2025-10-23 15:00:31.971476+00	2832b3e7-8333-425a-a4ab-7581168ed20f	2e78ba63-ebb7-4934-b9cd-8fbf75364e2d
b02727b3-a7d3-4a1d-97c6-b14d7606726d	2025-10-23 15:00:55.056851+00	2025-10-27 07:00:00+00	CREATED	2025-10-23 15:00:55.056883+00	209cef07-32da-43da-9bfd-b55a7e72753a	2e78ba63-ebb7-4934-b9cd-8fbf75364e2d
a36bbc06-0590-43b9-87fd-195e6d9da1f4	2025-10-23 15:01:34.665212+00	2025-10-24 08:00:00+00	CREATED	2025-10-23 15:01:34.665247+00	26399ff0-9785-48a9-972c-f8f0ce53d309	a67c29e5-4ad9-4014-9dfb-f32a7300c9d6
2d110095-caa0-45fa-8ad3-8b04e4b488b0	2025-10-23 15:02:16.781013+00	2025-10-24 14:00:00+00	CREATED	2025-10-23 15:02:16.781044+00	209cef07-32da-43da-9bfd-b55a7e72753a	a67c29e5-4ad9-4014-9dfb-f32a7300c9d6
270efa99-3d90-4be6-8a50-de0ea81f0027	2025-10-23 15:02:38.98959+00	2025-10-25 09:00:00+00	CREATED	2025-10-23 15:02:38.989622+00	2832b3e7-8333-425a-a4ab-7581168ed20f	a67c29e5-4ad9-4014-9dfb-f32a7300c9d6
\.


--
-- Data for Name: doctors; Type: TABLE DATA; Schema: public; Owner: plexsalud_test_user
--

COPY public.doctors (uuid, created_at, full_name, specialty, updated_at, user_uuid) FROM stdin;
26399ff0-9785-48a9-972c-f8f0ce53d309	2025-10-23 14:55:11.802	javi	neurology	2025-10-23 14:55:11.802	f4222788-2b67-46a0-a30f-167c77c2ac50
209cef07-32da-43da-9bfd-b55a7e72753a	2025-10-23 14:57:37.42	michelle	cardiology	2025-10-23 14:57:37.42	9a6b61de-7e03-4d6f-8478-de5e18e1484e
2832b3e7-8333-425a-a4ab-7581168ed20f	2025-10-23 14:58:18.261	abdon	oncology	2025-10-23 14:58:18.261	3ee6f792-95b8-4697-9d12-6655f52a1a35
\.


--
-- Data for Name: nurses; Type: TABLE DATA; Schema: public; Owner: plexsalud_test_user
--

COPY public.nurses (uuid, created_at, full_name, updated_at, user_uuid) FROM stdin;
\.


--
-- Data for Name: patients; Type: TABLE DATA; Schema: public; Owner: plexsalud_test_user
--

COPY public.patients (uuid, created_at, full_name, updated_at, user_uuid) FROM stdin;
a67c29e5-4ad9-4014-9dfb-f32a7300c9d6	2025-10-23 14:58:50.106	alberto	2025-10-23 14:58:50.106	d0901ceb-fd52-44c1-a1ee-6cd9419c584d
2e78ba63-ebb7-4934-b9cd-8fbf75364e2d	2025-10-23 14:59:18.739	yeray	2025-10-23 14:59:18.739	8012934f-70ea-4676-af05-04f4d9dc3bef
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: plexsalud_test_user
--

COPY public.users (uuid, created_at, email, password, role, updated_at) FROM stdin;
f4222788-2b67-46a0-a30f-167c77c2ac50	2025-10-23 14:37:46.113	javi@javi.com	$2a$10$NW4aZ5Soa63iPxwPZ0Ei0eOCoQvGAE02vRmsEazGs1LzdHN3aP8oG	DOCTOR	2025-10-23 14:37:46.113
3ee6f792-95b8-4697-9d12-6655f52a1a35	2025-10-23 14:38:27.61	abdon@abdon.com	$2a$10$X5l1shufLE0Kwy7S3Tuhf.vTYmY5iTbyBu.tSyZUa5oKfdn2zckge	DOCTOR	2025-10-23 14:38:27.61
9a6b61de-7e03-4d6f-8478-de5e18e1484e	2025-10-23 14:39:05.14	michelle@michelle.com	$2a$10$4C0acWesvijaU0TySeL4C.LxOLEERLR7jMjA8Ys0TpvJjRVHHnJyW	DOCTOR	2025-10-23 14:39:05.14
d0901ceb-fd52-44c1-a1ee-6cd9419c584d	2025-10-23 14:39:41.949	alberto@alberto.com	$2a$10$PgGxlhiGv7RIB3A9Mmp72uDYnaunCpFL2/JXqwuA/tY6j5gaFNsf2	PATIENT	2025-10-23 14:39:41.949
8012934f-70ea-4676-af05-04f4d9dc3bef	2025-10-23 14:39:58.628	yeray@yeray.com	$2a$10$cauep9euYe/q9AEqLM7Rl./8qG0vydGL1fRmJbStuaOYZxHFELLrq	PATIENT	2025-10-23 14:39:58.628
\.


--
-- Name: appointment appointment_pkey; Type: CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT appointment_pkey PRIMARY KEY (uuid);


--
-- Name: doctors doctors_pkey; Type: CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_pkey PRIMARY KEY (uuid);


--
-- Name: nurses nurses_pkey; Type: CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.nurses
    ADD CONSTRAINT nurses_pkey PRIMARY KEY (uuid);


--
-- Name: patients patients_pkey; Type: CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.patients
    ADD CONSTRAINT patients_pkey PRIMARY KEY (uuid);


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: nurses uk93evl2fvuyrjqtplnvgdq9ylm; Type: CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.nurses
    ADD CONSTRAINT uk93evl2fvuyrjqtplnvgdq9ylm UNIQUE (user_uuid);


--
-- Name: doctors uk96c6cg5dy56556fk9tcs27433; Type: CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT uk96c6cg5dy56556fk9tcs27433 UNIQUE (user_uuid);


--
-- Name: patients ukivnac5smes0u9itakt83slobi; Type: CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.patients
    ADD CONSTRAINT ukivnac5smes0u9itakt83slobi UNIQUE (user_uuid);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (uuid);


--
-- Name: doctors fkd8nu1nm9vq7p4uno7p9xm1sp3; Type: FK CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT fkd8nu1nm9vq7p4uno7p9xm1sp3 FOREIGN KEY (user_uuid) REFERENCES public.users(uuid);


--
-- Name: appointment fkh8qs8rli9s3y6l2ehi50lara2; Type: FK CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT fkh8qs8rli9s3y6l2ehi50lara2 FOREIGN KEY (doctor_uuid) REFERENCES public.doctors(uuid);


--
-- Name: nurses fkic1g3ncrntxh6rygh1i3m9m3q; Type: FK CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.nurses
    ADD CONSTRAINT fkic1g3ncrntxh6rygh1i3m9m3q FOREIGN KEY (user_uuid) REFERENCES public.users(uuid);


--
-- Name: appointment fkp35ryfuqojnnt0o5hr6e9vp25; Type: FK CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT fkp35ryfuqojnnt0o5hr6e9vp25 FOREIGN KEY (patient_uuid) REFERENCES public.patients(uuid);


--
-- Name: patients fkr0fdfsrgql0uw6frepcyje1l0; Type: FK CONSTRAINT; Schema: public; Owner: plexsalud_test_user
--

ALTER TABLE ONLY public.patients
    ADD CONSTRAINT fkr0fdfsrgql0uw6frepcyje1l0 FOREIGN KEY (user_uuid) REFERENCES public.users(uuid);


--
-- PostgreSQL database dump complete
--

\unrestrict Gd0ejAjZoX9dlWqZ22elzbSN3ZEnua7gq8STH5of4kXVpxU3KHsDYVQif1yXvaV

