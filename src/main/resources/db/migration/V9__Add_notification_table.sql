create table notification
(
	id int auto_increment,
	user_id int,
	comment_id int,
	state int default 0
);

create unique index notification_id_uindex
	on notification (id);

alter table notification
	add constraint notification_pk
		primary key (id);

