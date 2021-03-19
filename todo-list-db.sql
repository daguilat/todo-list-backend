drop database todolistdb;
drop user todolist;
create user todolist with password 'password';
create database todolistdb with template=template0 owner todolist;
\connect todolistdb;

alter default privileges grant all on tables to todolist;
alter default privileges grant all on sequences to todolist;

create table users(
    user_id integer primary key not null,
    username varchar(30) not null,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    password text not null
);

create table task(
    task_id integer primary key not null,
    title varchar(50) not null,
    description varchar(100) not null,
    user_id integer not null,
    state varchar(30) not null
);

create table audit(
    audit_id integer primary key not null,
    user_id integer not null,
    task_id integer,
    activity varchar(100) not null,
    date timestamp not null
);

alter table task add constraint task_users_fk
foreign key (user_id) references users(user_id);

alter table audit add constraint audit_users_fk
foreign key (user_id) references users(user_id);

alter table audit add constraint audit_tasks_fk
foreign key (task_id) references task(task_id);

create sequence user_id_seq increment 1 start 1;
create sequence task_id_seq increment 1 start 1;
create sequence audit_id_seq increment 1 start 1;