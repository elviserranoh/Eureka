--liquibase formatted sql

--changeset elvis.serrano:tb_users_changelog.0.1 context:dev,prod
--comment tb_users creation tag: tb_users_changelog.0.1

--
-- tb_users
--

create table TB_USERS(
    id uuid primary key,
    name varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null,
    created timestamp WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    modified timestamp WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_login timestamp WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    is_enabled bool default true
);

INSERT INTO TB_USERS(id, name, email, password) VALUES ('69799999-0dce-4120-ad13-cb8e44d356b8', 'Elvis Serrano', 'elviserranoh@gmail.com', '$2a$10$c6VvM14Zt2bZmANjMthQC.mU/HxtYjBR5acvbcGjG4In4oewXYORm');

--rollback DROP TABLE TB_USERS

--changeset elvis.serrano:tb_phones_changelog.0.1 context:dev,prod
--comment tb_phones creation tag: tb_phones_changelog.0.1

--
-- tb_phones
--

create table TB_PHONES(
    id bigserial primary key,
    user_id uuid not null,
    number bigint not null,
    city_code int not null,
    country_code int not null,
    FOREIGN KEY (user_id) REFERENCES TB_USERS(id)
);

INSERT INTO TB_PHONES(id, user_id, number, city_code, country_code) VALUES (1, '69799999-0dce-4120-ad13-cb8e44d356b8', 4148090961, 6101, 58);
INSERT INTO TB_PHONES(id, user_id, number, city_code, country_code) VALUES (2, '69799999-0dce-4120-ad13-cb8e44d356b8', 4249876278, 6101, 58);
INSERT INTO TB_PHONES(id, user_id, number, city_code, country_code) VALUES (3, '69799999-0dce-4120-ad13-cb8e44d356b8', 4522049, 293, 58);

--rollback DROP TABLE TB_PHONES

--changeset elvis.serrano:tb_user_role_changelog.0.1 context:dev,prod
--comment tb_phones creation tag: tb_user_role_changelog.0.1

--
-- TB_USER_ROLE
--

create table TB_USER_ROLE(
    id uuid primary key,
    name varchar(255) unique not null
);

INSERT INTO TB_USER_ROLE(id, name) VALUES ('d5fc8790-4625-4a2c-a5d5-abd233107bf1', 'USER');
INSERT INTO TB_USER_ROLE(id, name) VALUES ('432c8171-0c40-4b0f-ba65-393637df32bf', 'ADMIN');

--rollback DROP TABLE TB_USER_ROLE

--changeset elvis.serrano:tb_user_user_role_changelog.0.1 context:dev,prod
--comment tb_phones creation tag: tb_user_user_role_changelog.0.1

--
-- TB_USER_USER_ROLE
--

create table TB_USER_USER_ROLE(
    user_id uuid references TB_USERS(id),
    role_id uuid references TB_USER_ROLE(id),
    UNIQUE (user_id, role_id)
);

INSERT INTO TB_USER_USER_ROLE VALUES ('69799999-0dce-4120-ad13-cb8e44d356b8', '432c8171-0c40-4b0f-ba65-393637df32bf');

--rollback DROP TABLE TB_USER_USER_ROLE
