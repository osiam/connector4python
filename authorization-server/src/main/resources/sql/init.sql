ALTER TABLE ONLY public.scim_enterprise DROP CONSTRAINT fke1bc510cae52e63f;
ALTER TABLE ONLY public.scim_photo DROP CONSTRAINT fkdd4f01a7738674d5;
ALTER TABLE ONLY public.scim_group DROP CONSTRAINT fkdcd4b9f4e9e686e0;
ALTER TABLE ONLY public.scim_email DROP CONSTRAINT fkdcb60f11738674d5;
ALTER TABLE ONLY public.scim_phonenumber DROP CONSTRAINT fkd9f3520c738674d5;
ALTER TABLE ONLY public.scim_address DROP CONSTRAINT fka4a85629738674d5;
ALTER TABLE ONLY public.scim_certificate DROP CONSTRAINT fk956dd94c738674d5;
ALTER TABLE ONLY public.scim_group_scim_id DROP CONSTRAINT fk8d2c327bc347d0ba;
ALTER TABLE ONLY public.scim_group_scim_id DROP CONSTRAINT fk8d2c327b6d23d136;
ALTER TABLE ONLY public.scim_im DROP CONSTRAINT fk725705cf738674d5;
ALTER TABLE ONLY public.scim_user_scim_roles DROP CONSTRAINT fk70e4b45be638e451;
ALTER TABLE ONLY public.scim_user_scim_roles DROP CONSTRAINT fk70e4b45babdb6640;
ALTER TABLE ONLY public.scim_user_scim_group DROP CONSTRAINT fk704b1c1dabdb6640;
ALTER TABLE ONLY public.scim_user_scim_group DROP CONSTRAINT fk704b1c1d17c2116;
ALTER TABLE ONLY public.scim_user DROP CONSTRAINT fk38b265b6e9e686e0;
ALTER TABLE ONLY public.scim_user DROP CONSTRAINT fk38b265b627b5137b;
ALTER TABLE ONLY public.scim_user_scim_address DROP CONSTRAINT fk340ed212abdb6640;
ALTER TABLE ONLY public.scim_user_scim_address DROP CONSTRAINT fk340ed212353ef531;
ALTER TABLE ONLY public.scim_user_scim_entitlements DROP CONSTRAINT fk2d322588ef67251f;
ALTER TABLE ONLY public.scim_user_scim_entitlements DROP CONSTRAINT fk2d322588abdb6640;
ALTER TABLE ONLY public.scim_user_additional DROP CONSTRAINT fk20575a504c8bba87;
ALTER TABLE ONLY public.scim_user_scim_roles DROP CONSTRAINT scim_user_scim_roles_pkey;
ALTER TABLE ONLY public.scim_user_scim_group DROP CONSTRAINT scim_user_scim_group_pkey;
ALTER TABLE ONLY public.scim_user_scim_entitlements DROP CONSTRAINT scim_user_scim_entitlements_pkey;
ALTER TABLE ONLY public.scim_user_scim_address DROP CONSTRAINT scim_user_scim_address_pkey;
ALTER TABLE ONLY public.scim_user DROP CONSTRAINT scim_user_pkey;
ALTER TABLE ONLY public.scim_roles DROP CONSTRAINT scim_roles_pkey;
ALTER TABLE ONLY public.scim_photo DROP CONSTRAINT scim_photo_pkey;
ALTER TABLE ONLY public.scim_phonenumber DROP CONSTRAINT scim_phonenumber_pkey;
ALTER TABLE ONLY public.scim_name DROP CONSTRAINT scim_name_pkey;
ALTER TABLE ONLY public.scim_meta DROP CONSTRAINT scim_meta_pkey;
ALTER TABLE ONLY public.scim_manager DROP CONSTRAINT scim_manager_pkey;
ALTER TABLE ONLY public.scim_im DROP CONSTRAINT scim_im_pkey;
ALTER TABLE ONLY public.scim_id DROP CONSTRAINT scim_id_pkey;
ALTER TABLE ONLY public.scim_group_scim_id DROP CONSTRAINT scim_group_scim_id_pkey;
ALTER TABLE ONLY public.scim_group DROP CONSTRAINT scim_group_pkey;
ALTER TABLE ONLY public.scim_entitlements DROP CONSTRAINT scim_entitlements_pkey;
ALTER TABLE ONLY public.scim_enterprise DROP CONSTRAINT scim_enterprise_pkey;
ALTER TABLE ONLY public.scim_email DROP CONSTRAINT scim_email_pkey;
ALTER TABLE ONLY public.scim_certificate DROP CONSTRAINT scim_certificate_pkey;
ALTER TABLE ONLY public.scim_address DROP CONSTRAINT scim_address_pkey;
ALTER TABLE ONLY public.database_scheme_version DROP CONSTRAINT database_scheme_version_pkey;
DROP TABLE public.scim_user_scim_roles;
DROP TABLE public.scim_user_scim_group;
DROP TABLE public.scim_user_scim_entitlements;
DROP TABLE public.scim_user_scim_address;
DROP TABLE public.scim_user_additional;
DROP TABLE public.scim_user;
DROP TABLE public.scim_roles;
DROP TABLE public.scim_photo;
DROP TABLE public.scim_phonenumber;
DROP TABLE public.scim_name;
DROP TABLE public.scim_meta;
DROP TABLE public.scim_manager;
DROP TABLE public.scim_im;
DROP TABLE public.scim_id;
DROP TABLE public.scim_group_scim_id;
DROP TABLE public.scim_group;
DROP TABLE public.scim_entitlements;
DROP TABLE public.scim_enterprise;
DROP TABLE public.scim_email;
DROP TABLE public.scim_certificate;
DROP TABLE public.scim_address;
DROP SEQUENCE public.hibernate_sequence;
DROP TABLE public.database_scheme_version;
DROP EXTENSION plpgsql;

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

CREATE TABLE database_scheme_version (
    version double precision NOT NULL
);


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: scim_address; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_address (
    id bigint NOT NULL,
    country character varying(255),
    formatted character varying(255),
    locality character varying(255),
    postalcode character varying(255),
    postgresql_does_not_like_primary boolean,
    region character varying(255),
    streetaddress character varying(255),
    type character varying(255),
    user_internal_id bigint
);


--
-- Name: scim_certificate; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_certificate (
    value character varying(255) NOT NULL,
    user_internal_id bigint
);


--
-- Name: scim_email; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_email (
    value character varying(255) NOT NULL,
    postgresql_does_not_like_primary boolean,
    type character varying(255),
    user_internal_id bigint NOT NULL
);


--
-- Name: scim_enterprise; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_enterprise (
    id bigint NOT NULL,
    costcenter character varying(255),
    department character varying(255),
    division character varying(255),
    employeenumber character varying(255),
    organization character varying(255),
    manager_id bigint
);


--
-- Name: scim_entitlements; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_entitlements (
    value character varying(255) NOT NULL
);


--
-- Name: scim_group; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_group (
    additional character varying(255),
    displayname character varying(255) NOT NULL,
    internal_id bigint NOT NULL
);


--
-- Name: scim_group_scim_id; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_group_scim_id (
    scim_group_internal_id bigint NOT NULL,
    members_internal_id bigint NOT NULL
);


--
-- Name: scim_id; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_id (
    internal_id bigint NOT NULL,
    externalid character varying(255) unique ,
    id uuid NOT NULL
);


--
-- Name: scim_im; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_im (
    value character varying(255) NOT NULL,
    type character varying(255),
    user_internal_id bigint
);


--
-- Name: scim_manager; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_manager (
    id bigint NOT NULL,
    displayname character varying(255),
    managerid bytea
);


--
-- Name: scim_meta; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_meta (
    id bigint NOT NULL,
    created timestamp without time zone,
    lastmodified timestamp without time zone,
    location character varying(255),
    version character varying(255)
);


--
-- Name: scim_name; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_name (
    id bigint NOT NULL,
    familyname character varying(255),
    formatted character varying(255),
    givenname character varying(255),
    honorificprefix character varying(255),
    honorificsuffix character varying(255),
    middlename character varying(255)
);


--
-- Name: scim_phonenumber; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_phonenumber (
    value character varying(255) NOT NULL,
    type character varying(255),
    user_internal_id bigint
);


--
-- Name: scim_photo; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_photo (
    value character varying(255) NOT NULL,
    type character varying(255),
    user_internal_id bigint
);


--
-- Name: scim_roles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_roles (
    value character varying(255) NOT NULL
);


--
-- Name: scim_user; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_user (
    active boolean,
    displayname character varying(255),
    locale character varying(255),
    nickname character varying(255),
    password character varying(255) NOT NULL,
    preferredlanguage character varying(255),
    profileurl character varying(255),
    timezone character varying(255),
    title character varying(255),
    username character varying(255) NOT NULL,
    usertype character varying(255),
    internal_id bigint NOT NULL,
    name_id bigint
);


--
-- Name: scim_user_additional; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_user_additional (
    id bigint NOT NULL,
    additional character varying(255)
);


--
-- Name: scim_user_scim_address; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_user_scim_address (
    scim_user_internal_id bigint NOT NULL,
    addresses_id bigint NOT NULL
);


--
-- Name: scim_user_scim_entitlements; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_user_scim_entitlements (
    scim_user_internal_id bigint NOT NULL,
    entitlements_value character varying(255) NOT NULL
);


--
-- Name: scim_user_scim_group; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_user_scim_group (
    scim_user_internal_id bigint NOT NULL,
    groups_internal_id bigint NOT NULL
);


--
-- Name: scim_user_scim_roles; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE scim_user_scim_roles (
    scim_user_internal_id bigint NOT NULL,
    roles_value character varying(255) NOT NULL
);


--
-- Data for Name: database_scheme_version; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO database_scheme_version VALUES (0.0200000000000000004);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- Data for Name: scim_address; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_certificate; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_email; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_enterprise; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_entitlements; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_group; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO scim_group VALUES (NULL, 'testGroup2', 2);


--
-- Data for Name: scim_group_scim_id; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_id; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO scim_id VALUES (1, NULL, 'cef9452e-00a9-4cec-a086-d171374ffbef');
INSERT INTO scim_id VALUES (2, NULL, '2a820312-67b3-4275-963d-b235c6525207');


--
-- Data for Name: scim_im; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_manager; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_meta; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_name; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_phonenumber; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_photo; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_roles; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_user; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO scim_user VALUES (NULL, NULL, NULL, NULL, 'cbae73fac0893291c4792ef19d158a589402288b35cb18fb8406e951b9d95f6b8b06a3526ffebe96ae0d91c04ae615a7fe2af362763db386ccbf3b55c29ae800', NULL, NULL, NULL, NULL, 'marissa', NULL, 1, NULL);
INSERT INTO scim_roles VALUES('USER');
INSERT INTO scim_user_scim_roles VALUES(1, 'USER');

--
-- Data for Name: scim_user_additional; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_user_scim_address; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_user_scim_entitlements; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_user_scim_group; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: scim_user_scim_roles; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Name: database_scheme_version_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY database_scheme_version
    ADD CONSTRAINT database_scheme_version_pkey PRIMARY KEY (version);


--
-- Name: scim_address_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_address
    ADD CONSTRAINT scim_address_pkey PRIMARY KEY (id);


--
-- Name: scim_certificate_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_certificate
    ADD CONSTRAINT scim_certificate_pkey PRIMARY KEY (value);


--
-- Name: scim_email_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_email
    ADD CONSTRAINT scim_email_pkey PRIMARY KEY (value);


--
-- Name: scim_enterprise_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_enterprise
    ADD CONSTRAINT scim_enterprise_pkey PRIMARY KEY (id);


--
-- Name: scim_entitlements_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_entitlements
    ADD CONSTRAINT scim_entitlements_pkey PRIMARY KEY (value);


--
-- Name: scim_group_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_group
    ADD CONSTRAINT scim_group_pkey PRIMARY KEY (internal_id);


--
-- Name: scim_group_scim_id_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_group_scim_id
    ADD CONSTRAINT scim_group_scim_id_pkey PRIMARY KEY (scim_group_internal_id, members_internal_id);


--
-- Name: scim_id_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_id
    ADD CONSTRAINT scim_id_pkey PRIMARY KEY (internal_id);


--
-- Name: scim_im_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_im
    ADD CONSTRAINT scim_im_pkey PRIMARY KEY (value);


--
-- Name: scim_manager_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_manager
    ADD CONSTRAINT scim_manager_pkey PRIMARY KEY (id);


--
-- Name: scim_meta_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_meta
    ADD CONSTRAINT scim_meta_pkey PRIMARY KEY (id);


--
-- Name: scim_name_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_name
    ADD CONSTRAINT scim_name_pkey PRIMARY KEY (id);


--
-- Name: scim_phonenumber_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_phonenumber
    ADD CONSTRAINT scim_phonenumber_pkey PRIMARY KEY (value);


--
-- Name: scim_photo_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_photo
    ADD CONSTRAINT scim_photo_pkey PRIMARY KEY (value);


--
-- Name: scim_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_roles
    ADD CONSTRAINT scim_roles_pkey PRIMARY KEY (value);


--
-- Name: scim_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_user
    ADD CONSTRAINT scim_user_pkey PRIMARY KEY (internal_id);


--
-- Name: scim_user_scim_address_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_user_scim_address
    ADD CONSTRAINT scim_user_scim_address_pkey PRIMARY KEY (scim_user_internal_id, addresses_id);


--
-- Name: scim_user_scim_entitlements_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_user_scim_entitlements
    ADD CONSTRAINT scim_user_scim_entitlements_pkey PRIMARY KEY (scim_user_internal_id, entitlements_value);


--
-- Name: scim_user_scim_group_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_user_scim_group
    ADD CONSTRAINT scim_user_scim_group_pkey PRIMARY KEY (scim_user_internal_id, groups_internal_id);


--
-- Name: scim_user_scim_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY scim_user_scim_roles
    ADD CONSTRAINT scim_user_scim_roles_pkey PRIMARY KEY (scim_user_internal_id, roles_value);


--
-- Name: fk20575a504c8bba87; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user_additional
    ADD CONSTRAINT fk20575a504c8bba87 FOREIGN KEY (id) REFERENCES scim_user(internal_id);


--
-- Name: fk2d322588abdb6640; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user_scim_entitlements
    ADD CONSTRAINT fk2d322588abdb6640 FOREIGN KEY (scim_user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fk2d322588ef67251f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user_scim_entitlements
    ADD CONSTRAINT fk2d322588ef67251f FOREIGN KEY (entitlements_value) REFERENCES scim_entitlements(value);


--
-- Name: fk340ed212353ef531; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user_scim_address
    ADD CONSTRAINT fk340ed212353ef531 FOREIGN KEY (addresses_id) REFERENCES scim_address(id);


--
-- Name: fk340ed212abdb6640; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user_scim_address
    ADD CONSTRAINT fk340ed212abdb6640 FOREIGN KEY (scim_user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fk38b265b627b5137b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user
    ADD CONSTRAINT fk38b265b627b5137b FOREIGN KEY (name_id) REFERENCES scim_name(id);


--
-- Name: fk38b265b6e9e686e0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user
    ADD CONSTRAINT fk38b265b6e9e686e0 FOREIGN KEY (internal_id) REFERENCES scim_id(internal_id);


--
-- Name: fk704b1c1d17c2116; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user_scim_group
    ADD CONSTRAINT fk704b1c1d17c2116 FOREIGN KEY (groups_internal_id) REFERENCES scim_group(internal_id);


--
-- Name: fk704b1c1dabdb6640; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user_scim_group
    ADD CONSTRAINT fk704b1c1dabdb6640 FOREIGN KEY (scim_user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fk70e4b45babdb6640; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user_scim_roles
    ADD CONSTRAINT fk70e4b45babdb6640 FOREIGN KEY (scim_user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fk70e4b45be638e451; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_user_scim_roles
    ADD CONSTRAINT fk70e4b45be638e451 FOREIGN KEY (roles_value) REFERENCES scim_roles(value);


--
-- Name: fk725705cf738674d5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_im
    ADD CONSTRAINT fk725705cf738674d5 FOREIGN KEY (user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fk8d2c327b6d23d136; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_group_scim_id
    ADD CONSTRAINT fk8d2c327b6d23d136 FOREIGN KEY (scim_group_internal_id) REFERENCES scim_group(internal_id);


--
-- Name: fk8d2c327bc347d0ba; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_group_scim_id
    ADD CONSTRAINT fk8d2c327bc347d0ba FOREIGN KEY (members_internal_id) REFERENCES scim_id(internal_id) ON DELETE CASCADE;


--
-- Name: fk956dd94c738674d5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_certificate
    ADD CONSTRAINT fk956dd94c738674d5 FOREIGN KEY (user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fka4a85629738674d5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_address
    ADD CONSTRAINT fka4a85629738674d5 FOREIGN KEY (user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fkd9f3520c738674d5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_phonenumber
    ADD CONSTRAINT fkd9f3520c738674d5 FOREIGN KEY (user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fkdcb60f11738674d5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_email
    ADD CONSTRAINT fkdcb60f11738674d5 FOREIGN KEY (user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fkdcd4b9f4e9e686e0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_group
    ADD CONSTRAINT fkdcd4b9f4e9e686e0 FOREIGN KEY (internal_id) REFERENCES scim_id(internal_id);


--
-- Name: fkdd4f01a7738674d5; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_photo
    ADD CONSTRAINT fkdd4f01a7738674d5 FOREIGN KEY (user_internal_id) REFERENCES scim_user(internal_id);


--
-- Name: fke1bc510cae52e63f; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY scim_enterprise
    ADD CONSTRAINT fke1bc510cae52e63f FOREIGN KEY (manager_id) REFERENCES scim_manager(id);


--
-- PostgreSQL database dump complete
--

