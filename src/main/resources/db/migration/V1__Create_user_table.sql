create table user
(
	id int auto_increment,
	account_id varchar(50),
	username varchar(50) not null,
	token varchar(35),
	gmt_create bigint,
	gmt_modified bigint,
	profile_picture varchar(200),
	platform varchar(20)
);

create unique index user_id_uindex
	on user (id);

create unique index user_token_uindex
	on user (token);

create unique index user_username_uindex
	on user (username);

alter table user
	add constraint user_pk
		primary key (id);