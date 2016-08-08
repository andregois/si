# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table arquivo (
  id                        varchar(255) not null,
  user_id                   varchar(255),
  pasta_id                  varchar(255),
  nome_do_arquivo           varchar(255),
  conteudo                  varchar(255),
  constraint pk_arquivo primary key (id))
;

create table pasta (
  id                        varchar(255) not null,
  user_id                   varchar(255),
  nome_da_pasta             varchar(255),
  constraint pk_pasta primary key (id))
;

create table usuario (
  id                        varchar(255) not null,
  email                     varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  constraint uq_usuario_email unique (email),
  constraint uq_usuario_username unique (username),
  constraint pk_usuario primary key (id))
;

create sequence arquivo_seq;

create sequence pasta_seq;

create sequence usuario_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists arquivo;

drop table if exists pasta;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists arquivo_seq;

drop sequence if exists pasta_seq;

drop sequence if exists usuario_seq;

