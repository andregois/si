# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table arquivo (
  id                        varchar(255) not null,
  pasta_id                  varchar(255) not null,
  name                      varchar(255),
  content                   varchar(255),
  constraint pk_arquivo primary key (id))
;

create table pasta (
  id                        varchar(255) not null,
  name                      varchar(255),
  constraint pk_pasta primary key (id))
;

create table usuario (
  id                        varchar(255) not null,
  email                     varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  root_id                   varchar(255),
  constraint uq_usuario_email unique (email),
  constraint uq_usuario_username unique (username),
  constraint pk_usuario primary key (id))
;


create table arquivo_usuario (
  arquivo_id                     varchar(255) not null,
  usuario_id                     varchar(255) not null,
  constraint pk_arquivo_usuario primary key (arquivo_id, usuario_id))
;
create sequence arquivo_seq;

create sequence pasta_seq;

create sequence usuario_seq;

alter table arquivo add constraint fk_arquivo_pasta_1 foreign key (pasta_id) references pasta (id) on delete restrict on update restrict;
create index ix_arquivo_pasta_1 on arquivo (pasta_id);
alter table usuario add constraint fk_usuario_root_2 foreign key (root_id) references pasta (id) on delete restrict on update restrict;
create index ix_usuario_root_2 on usuario (root_id);



alter table arquivo_usuario add constraint fk_arquivo_usuario_arquivo_01 foreign key (arquivo_id) references arquivo (id) on delete restrict on update restrict;

alter table arquivo_usuario add constraint fk_arquivo_usuario_usuario_02 foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists arquivo;

drop table if exists arquivo_usuario;

drop table if exists pasta;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists arquivo_seq;

drop sequence if exists pasta_seq;

drop sequence if exists usuario_seq;

