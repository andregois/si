# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table arquivo (
  id                        bigint not null,
  nome_do_arquivo           varchar(255),
  conteudo                  varchar(255),
  constraint pk_arquivo primary key (id))
;

create table usuario (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_usuario primary key (id))
;

create sequence arquivo_seq;

create sequence usuario_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists arquivo;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists arquivo_seq;

drop sequence if exists usuario_seq;

