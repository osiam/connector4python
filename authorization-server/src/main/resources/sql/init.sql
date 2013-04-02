CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE database_scheme_version(
  version double precision NOT NULL PRIMARY KEY
);

--
-- Name: scim_certificate; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_certificate (
    id bigint NOT NULL,
    value character varying(255),
    user_id bigint
);




--
-- Name: scim_email; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_email (
    id bigint NOT NULL,
    postgresql_does_not_like_primary boolean,
    type character varying(255),
    value character varying(255),
    user_id bigint
);



--
-- Name: scim_address; Type: TABLE; Schema: public;
--

CREATE TABLE scim_address (
  id bigint NOT NULL,
  postgresql_does_not_like_primary boolean,
  type character varying(255),
  formatted character varying(255),
  streetAddress character varying(255),
  locality character varying(255),
  region character varying(255),
  country character varying(255),
  postalCode character varying(255),
  user_id bigint
);



--
-- Name: scim_enterprise; Type: TABLE; Schema: public;  
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
-- Name: scim_entitlements; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_entitlements (
    id bigint NOT NULL,
    value character varying(255)
);




--
-- Name: scim_group_scim_member; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_group_scim_member (
    scim_group_id bigint NOT NULL,
    members_id bigint NOT NULL
);



--
-- Name: scim_group; Type: TABLE; Schema: public;
--

CREATE TABLE scim_group (
  id bigint NOT NULL,
  value character varying(255),
  displayName character varying(255),
  additional character varying(255)
);


--
-- Name: scim_im; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_im (
    id bigint NOT NULL,
    type character varying(255),
    value character varying(255),
    user_id bigint
);


--
-- Name: scim_manager; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_manager (
    id bigint NOT NULL,
    displayname character varying(255),
    managerid bytea
);




--
-- Name: scim_member; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_member (
    id bigint NOT NULL,
    display character varying(255),
    value bytea
);




--
-- Name: scim_meta; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_meta (
    id bigint NOT NULL,
    created timestamp without time zone,
    lastmodified timestamp without time zone,
    location character varying(255),
    version character varying(255)
);




--
-- Name: scim_name; Type: TABLE; Schema: public;  
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
-- Name: scim_phonenumber; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_phonenumber (
    id bigint NOT NULL,
    type character varying(255),
    value character varying(255),
    user_id bigint
);




--
-- Name: scim_photo; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_photo (
    id bigint NOT NULL,
    type character varying(255),
    value character varying(255),
    user_id bigint
);




--
-- Name: scim_roles; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_roles (
    id bigint NOT NULL,
    value character varying(255)
);



--
-- Name: scim_user; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_user (
    id bigint NOT NULL,
    internalId UUID NOT NULL UNIQUE,
    active boolean,
    displayname character varying(255),
    externalid character varying(255),
    locale character varying(255),
    nickname character varying(255) ,
    password character varying(255) NOT NULL,
    preferredlanguage character varying(255),
    profileurl character varying(255),
    timezone character varying(255),
    title character varying(255),
    username character varying(255) NOT NULL UNIQUE,
    usertype character varying(255),
    name_id bigint,
    additional_id bigint
);



--
-- Name: scim_user_additional; Type: TABLE; Schema: public;
--

CREATE TABLE scim_user_additional (
    id bigint NOT NULL,
    additional character varying(255)
);



--
-- Name: scim_user_scim_address; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_user_scim_address (
    scim_user_id bigint NOT NULL,
    addresses_id bigint NOT NULL
);




--
-- Name: scim_user_scim_entitlements; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_user_scim_entitlements (
    scim_user_id bigint NOT NULL,
    entitlements_id bigint NOT NULL
);




--
-- Name: scim_user_scim_group; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_user_scim_group (
    scim_user_id bigint NOT NULL,
    groups_id bigint NOT NULL
);




--
-- Name: scim_user_scim_roles; Type: TABLE; Schema: public;  
--

CREATE TABLE scim_user_scim_roles (
    scim_user_id bigint NOT NULL,
    roles_id bigint NOT NULL
);



--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; 
--

-- we need at least one role and one user
SELECT pg_catalog.setval('hibernate_sequence', 3, false);


INSERT INTO database_scheme_version (version) values (0.02);


--
-- Data for Name: scim_certificate; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_email; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_enterprise; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_entitlements; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_group_scim_member; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_group; Type: TABLE DATA; Schema: public;
--



--
-- Data for Name: scim_im; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_manager; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_member; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_meta; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_name; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_phonenumber; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_photo; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_roles; Type: TABLE DATA; Schema: public; 
--

INSERT INTO scim_roles (id, value) values (2, 'USER');

--
-- Data for Name: scim_user; Type: TABLE DATA; Schema: public; 
--

INSERT INTO scim_user (id, internalId, externalid, password, username) VALUES (1, 'CEF9452E-00A9-4CEC-A086-D171374FFBEF', 'marissa', '60b26cf7a6f8bb56a8e725aaf9566ad51948810c482e783200a3e64951d1c70172ca47ce0f46c44ae52912068a0e7b7515c740358bd103590518d31bfdf100b1', 'marissa');

--
-- Data for Name: scim_user_scim_address; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_user_scim_entitlements; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_user_scim_group; Type: TABLE DATA; Schema: public; 
--



--
-- Data for Name: scim_user_scim_roles; Type: TABLE DATA; Schema: public; 
--

INSERT INTO scim_user_scim_roles (scim_user_id , roles_id) values (1, 2);

--
-- Name: scim_certificate_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_certificate
    ADD CONSTRAINT scim_certificate_pkey PRIMARY KEY (id);


--
-- Name: scim_email_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_email
    ADD CONSTRAINT scim_email_pkey PRIMARY KEY (id);


--
-- Name: scim_enterprise_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_enterprise
    ADD CONSTRAINT scim_enterprise_pkey PRIMARY KEY (id);


--
-- Name: scim_entitlements_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_entitlements
    ADD CONSTRAINT scim_entitlements_pkey PRIMARY KEY (id);



--
-- Name: scim_address_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY scim_address
    ADD CONSTRAINT scim_address_pkey PRIMARY KEY (id);



--
-- Name: scim_group_pkey; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY scim_group
    ADD CONSTRAINT scim_group_pkey PRIMARY KEY (id);


--
-- Name: scim_im_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_im
    ADD CONSTRAINT scim_im_pkey PRIMARY KEY (id);


--
-- Name: scim_manager_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_manager
    ADD CONSTRAINT scim_manager_pkey PRIMARY KEY (id);


--
-- Name: scim_member_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_member
    ADD CONSTRAINT scim_member_pkey PRIMARY KEY (id);


--
-- Name: scim_meta_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_meta
    ADD CONSTRAINT scim_meta_pkey PRIMARY KEY (id);


--
-- Name: scim_name_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_name
    ADD CONSTRAINT scim_name_pkey PRIMARY KEY (id);


--
-- Name: scim_phonenumber_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_phonenumber
    ADD CONSTRAINT scim_phonenumber_pkey PRIMARY KEY (id);


--
-- Name: scim_photo_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_photo
    ADD CONSTRAINT scim_photo_pkey PRIMARY KEY (id);


--
-- Name: scim_roles_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_roles
    ADD CONSTRAINT scim_roles_pkey PRIMARY KEY (id);


--
-- Name: scim_user_pkey; Type: CONSTRAINT; Schema: public;  
--

ALTER TABLE ONLY scim_user
    ADD CONSTRAINT scim_user_pkey PRIMARY KEY (id);



--
-- Name: scim_user_additional; Type: CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY scim_user_additional
    ADD CONSTRAINT scim_user_additional_pkey PRIMARY KEY (id);




--
-- Name: fk2d3225882cd755b0; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_user_scim_entitlements
    ADD CONSTRAINT fk2d3225882cd755b0 FOREIGN KEY (scim_user_id) REFERENCES scim_user(id);


--
-- Name: fk2d3225884aa1835b; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_user_scim_entitlements
    ADD CONSTRAINT fk2d3225884aa1835b FOREIGN KEY (entitlements_id) REFERENCES scim_entitlements(id);


--
-- Name: fk340ed2122cd755b0; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_user_scim_address
    ADD CONSTRAINT fk340ed2122cd755b0 FOREIGN KEY (scim_user_id) REFERENCES scim_user(id);



--
-- Name: fk340ed9122cd755b0; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY scim_user_scim_address
ADD CONSTRAINT fk340ed9122cd755b0 FOREIGN KEY (addresses_id) REFERENCES scim_address(id);



--
-- Name: fk38b265b627b5137b; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_user
    ADD CONSTRAINT fk38b265b627b5137b FOREIGN KEY (name_id) REFERENCES scim_name(id);


--
-- Name: fk38b265b927b5137b; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY scim_user
    ADD CONSTRAINT fk38b265b927b5137b FOREIGN KEY (additional_id) REFERENCES scim_user_additional(id);


--
-- Name: fk704b1c1d2cd755b0; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_user_scim_group
    ADD CONSTRAINT fk704b1c1d2cd755b0 FOREIGN KEY (scim_user_id) REFERENCES scim_user(id);


--
-- Name: fk704b1c1d9cd755b0; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY scim_user_scim_group
ADD CONSTRAINT fk704b1c1d9cd755b0 FOREIGN KEY (groups_id) REFERENCES scim_group(id);



--
-- Name: fk70e4b45b2cd755b0; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_user_scim_roles
    ADD CONSTRAINT fk70e4b45b2cd755b0 FOREIGN KEY (scim_user_id) REFERENCES scim_user(id);


--
-- Name: fk70e4b45ba7c830bf; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_user_scim_roles
    ADD CONSTRAINT fk70e4b45ba7c830bf FOREIGN KEY (roles_id) REFERENCES scim_roles(id);



--
-- Name: fk725705cf43c69b7b; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_im
    ADD CONSTRAINT fk725705cf43c69b7b FOREIGN KEY (user_id) REFERENCES scim_user(id);


--
-- Name: fk956dd94c43c69b7b; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_certificate
    ADD CONSTRAINT fk956dd94c43c69b7b FOREIGN KEY (user_id) REFERENCES scim_user(id);


--
-- Name: fkd9f3520c43c69b7b; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_phonenumber
    ADD CONSTRAINT fkd9f3520c43c69b7b FOREIGN KEY (user_id) REFERENCES scim_user(id);


--
-- Name: fkdcb60f1143c69b7b; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_email
    ADD CONSTRAINT fkdcb60f1143c69b7b FOREIGN KEY (user_id) REFERENCES scim_user(id);


--
-- Name: fkdd4f01a743c69b7b; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_photo
    ADD CONSTRAINT fkdd4f01a743c69b7b FOREIGN KEY (user_id) REFERENCES scim_user(id);


--
-- Name: fke1bc510cae52e63f; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_enterprise
    ADD CONSTRAINT fke1bc510cae52e63f FOREIGN KEY (manager_id) REFERENCES scim_manager(id);


--
-- Name: fke59cfffa843a1b7c; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY scim_group_scim_member
    ADD CONSTRAINT fke59cfffa843a1b7c FOREIGN KEY (members_id) REFERENCES scim_member(id);


--
-- Name: fk90e4b45ba9c830bf; Type: FK CONSTRAINT; Schema: public;
--

ALTER TABLE ONLY scim_group_scim_member
ADD CONSTRAINT fk90e4b45ba9c830bf FOREIGN KEY (scim_group_id) REFERENCES scim_group(id);


--
-- PostgreSQL database dump complete
--

