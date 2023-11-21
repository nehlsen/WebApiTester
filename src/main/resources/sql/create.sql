create table plan (created timestamp(6), updated timestamp(6), uuid uuid not null, name varchar(255), primary key (uuid));
create table plan_tasks (plan_entity_uuid uuid not null, tasks_uuid uuid not null unique);
create table t_assertion (expected_status_code integer, created timestamp(6), maximum_request_time_millis bigint, updated timestamp(6), uuid uuid not null, dtype varchar(31) not null, primary key (uuid));
create table task (created timestamp(6), updated timestamp(6), uuid uuid not null, dtype varchar(31) not null, name varchar(255), uri bytea, primary key (uuid));
create table task_assertions (assertions_uuid uuid not null unique, task_entity_uuid uuid not null);

alter table if exists plan_tasks add constraint FK2fq5ab00nw0vkxapu4yen7syg foreign key (tasks_uuid) references task;
alter table if exists plan_tasks add constraint FK6yyt6hb2sh5rqwo7784abrwtb foreign key (plan_entity_uuid) references plan;
alter table if exists task_assertions add constraint FKxmpqbescyqo2lbp7ps3hjks2 foreign key (assertions_uuid) references t_assertion;
alter table if exists task_assertions add constraint FK1cm493bbhqcbbfekx30odj6rf foreign key (task_entity_uuid) references task;