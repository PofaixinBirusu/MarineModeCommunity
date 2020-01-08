create table question
(
	id int auto_increment,
	title varchar(50),
	description text,
	gmt_create bigint,
	gmt_modified int,
	user_id int,
	comment_count int default 0,
	read_count int default 0,
	like_count int default 0,
	labels varchar(256)
);

create unique index question_id_uindex
	on question (id);

alter table question
	add constraint question_pk
		primary key (id);

