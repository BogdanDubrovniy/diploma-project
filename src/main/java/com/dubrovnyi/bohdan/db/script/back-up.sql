drop table if exists research;
drop table if exists sloc;
drop table if exists mi;
drop table if exists hv;

create table sloc(
  id integer not null auto_increment primary key,
  loc integer not null,
  com double not null
);

create table mi(
  id integer not null auto_increment primary key,
  mi_value double not null
);

create table hv(
  id integer not null auto_increment primary key,
  hv_value double not null
);

create table research(
  id integer not null auto_increment primary key,
  r_time date,
  script_name varchar(125) not null,
  sloc_id integer unique references sloc(id),
  mi_id integer unique references mi(id),
  hv_id integer unique references hv(id)

    on delete cascade
    on update restrict
);

insert into sloc values(default, 23, 0.1);
insert into sloc values(default, 25, 0.1);

insert into mi values(default, 5);
insert into mi values(default, 2);

insert into hv values(default, 15);
insert into hv values(default, 35);

insert into research values(default, '2017-10-10', 'some-path', 1, 1, 1);
insert into research values(default, '2017-11-10', 'some-path', 2, 2, 2);

