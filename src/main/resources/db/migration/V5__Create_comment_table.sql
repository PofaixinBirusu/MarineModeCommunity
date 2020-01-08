create table comment
(
	id int auto_increment,
	parent_id int,
	parent_type int,
	user_id int,
	like_count int default 0,
	gmt_create bigint,
	gmt_modified bigint
);

create unique index comment_id_uindex
	on comment (id);

alter table comment
	add constraint comment_pk
		primary key (id);

